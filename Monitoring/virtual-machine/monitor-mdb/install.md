## PostgreSQLをインストール
```
# yum -y localinstall https://download.postgresql.org/pub/repos/yum/10/redhat/rhel-7-x86_64/pgdg-centos10-10-2.noarch.rpm
# yum -y install postgresql10-server
# /usr/pgsql-10/bin/postgresql-10-setup initdb
# systemctl enable postgresql-10
# systemctl start postgresql-10
```

## firewalldの設定
```
# firewall-cmd --permanent --new-service=postgres

# firewall-cmd --permanent --service=postgres --set-short=postgres
# firewall-cmd --permanent --service=postgres --set-description=postgres
# firewall-cmd --permanent --service=postgres --add-port=5432/tcp

# firewall-cmd --permanent --add-service=postgres --zone=public

# firewall-cmd --reload
```

## DBユーザの追加

```
# su - postgres
$ psql

postgres=# CREATE USER "zabbix-user" WITH PASSWORD 'zabbix-pass';
postgres=# CREATE DATABASE "zabbix-db" WITH OWNER = "zabbix-user" ENCODING = "UTF-8";
postgres=# GRANT ALL PRIVILEGES ON DATABASE "zabbix-db" TO "zabbix-user";
```
## PostgreSQLの設定変更
```
# diff -u /var/lib/pgsql/10/data/postgresql.conf{.orig,}
--- /var/lib/pgsql/10/data/postgresql.conf.orig	2018-04-12 21:40:45.234423841 +0900
+++ /var/lib/pgsql/10/data/postgresql.conf	2018-04-12 21:42:46.521735708 +0900
@@ -56,7 +56,7 @@

 # - Connection Settings -

-#listen_addresses = 'localhost'		# what IP address(es) to listen on;
+listen_addresses = '*'		# what IP address(es) to listen on;
 					# comma-separated list of addresses;
 					# defaults to 'localhost'; use '*' for all
 					# (change requires restart)
@@ -110,7 +110,7 @@

 # - Memory -

-shared_buffers = 128MB			# min 128kB
+shared_buffers = 512MB			# min 128kB
 					# (change requires restart)
 #huge_pages = try			# on, off, or try
 					# (change requires restart)
```

```
# diff -u /var/lib/pgsql/10/data/pg_hba.conf{.orig,}
--- /var/lib/pgsql/10/data/pg_hba.conf.orig	2018-04-12 21:44:01.929016641 +0900
+++ /var/lib/pgsql/10/data/pg_hba.conf	2018-04-14 10:08:29.006832337 +0900
@@ -77,9 +77,10 @@
 # TYPE  DATABASE        USER            ADDRESS                 METHOD

 # "local" is for Unix domain socket connections only
-local   all             all                                     peer
+local   all             all                                     md5
 # IPv4 local connections:
 host    all             all             127.0.0.1/32            ident
+host    all             all             192.168.1.114/32        md5
 # IPv6 local connections:
 host    all             all             ::1/128                 ident
 # Allow replication connections from localhost, by a user with the
```
```
# systemctl restart postgresql-10
```

## Zabbixのインストール後のSQL実行
ZabbixのインストールでダウンロードしたSQLをアップロードする。
```
$ scp -P 22 ./create.sql.gz db@192.168.1.113:/tmp
```

SQLの実行
```
# chown postgres:postgres create.sql.gz
# su - postgres

$ cd /tmp
$ zcat create.sql.gz  | psql -U zabbix-user -d zabbix-db

```
