## PostgreSQLをインストール
```
# yum -y localinstall https://download.postgresql.org/pub/repos/yum/10/redhat/rhel-7-x86_64/pgdg-centos10-10-2.noarch.rpm
# yum -y install postgresql10-server
# /usr/pgsql-10/bin/postgresql-10-setup initdb
# systemctl enable postgresql-10
# systemctl start postgresql-10
```

## DBユーザの追加

```
# su - postgres
$ psql

postgres=# CREATE USER "monitor-user" WITH PASSWORD 'monitor-pass';
postgres=# CREATE DATABASE "monitor-db" WITH OWNER = "monitor-user" ENCODING = "UTF-8";
postgres=# GRANT ALL PRIVILEGES ON DATABASE "monitor-db" TO "monitor-user";
```

## PostgreSQLの設定変更

```
# diff -u postgresql.conf{.orig,}
--- postgresql.conf.orig	2018-04-07 10:37:17.540805243 +0900
+++ postgresql.conf	2018-04-07 10:41:30.253754535 +0900
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
@@ -424,7 +424,7 @@
 					#   fatal
 					#   panic (effectively off)

-#log_min_duration_statement = -1	# -1 is disabled, 0 logs all statements
+log_min_duration_statement = 100	# -1 is disabled, 0 logs all statements
 					# and their durations, > 0 logs only
 					# statements running at least this number
 					# of milliseconds
```
```
# diff pg_hba.conf{orig,}
82a83
> host    all             all             192.168.1.110/32        md5
```
```
# systemctl start postgresql-10
```
