## インストール
```
# rpm -ivh  https://repo.zabbix.com/zabbix/4.0/rhel/7/x86_64/zabbix-release-4.0-1.el7.noarch.rpm
# yum install epel-release
# yum install zabbix-server-pgsql
# yum install zabbix-web-pgsql
# yum install zabbix-get
```

## SQLのダウンロード
```
$ scp -P 22 zabbix@192.168.1.114:/usr/share/doc/zabbix-server-pgsql-3.4.8/create.sql.gz .
```

## firewalldの設定
```
# firewall-cmd --add-port=10051/tcp --zone=public --permanent
# firewall-cmd --add-service=http --zone=public --permanent
# systemctl restart firewalld
```

## SELinuxの設定
```
# setsebool -P httpd_can_network_connect_db on
# setsebool -P httpd_can_connect_zabbix on
# setsebool -P postgresql_port_t on

# /var/log/zabbix
# cd semodule -i zabbix_server.pp
```

## rlimitの設定
```
# yum install policycoreutils-python
# systemctl start server.service
# systemctl stop server.service
# grep zabbix_server /var/log/audit/audit.log | audit2allow -M zabbix-limit
# semodule -i zabbix-limit.pp
```


## 設定ファイルの変更
```
# vi /etc/zabbix/zabbix_server.conf
```
- DBHost
- DBName
- DBUser
- DBPassword
を変更した。

```
# diff /etc/php.ini{.orig,}
878a879
> date.timezone = Asia/Tokyo
```

## サービスの起動
```
# systemctl start zabbix-server.service
# systemctl enable zabbix-server
# service httpd restart
```


http://192.168.1.114/zabbix/setup.php
