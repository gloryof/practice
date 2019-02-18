## Apacheをインストール
```
# yum install httpd
```

## 設定ファイル
```
# cd /etc/httpd/conf
# diff -u httpd.conf{.orig,}
--- httpd.conf.orig	2018-04-11 21:27:25.939995499 +0900
+++ httpd.conf	2018-04-11 21:30:30.208929184 +0900
@@ -351,3 +351,6 @@
 #
 # Load config files in the "/etc/httpd/conf.d" directory, if any.
 IncludeOptional conf.d/*.conf
+
+ProxyPass / http://192.168.1.110:8080/
+ProxyPassReverse / http://192.168.1.110:8080/
```
## firewalldの設定
```
# firewall-cmd --permanent --add-service=http --zone=public

# firewall-cmd --reload
```

## SELinuxの設定
```
# /usr/sbin/setsebool -P httpd_can_network_connect 1
```

## Systemd
```
# systemctl start httpd
# systemctl enable httpd
```


## Zabbix-Agentのインストール
```
# rpm -ivh https://repo.zabbix.com/zabbix/4.0/rhel/7/x86_64/zabbix-release-4.0-1.el7.noarch.rpm
# yum install zabbix-agent
```

```
# diff -u /etc/zabbix/zabbix_agentd.conf{.orig,}
--- /etc/zabbix/zabbix_agentd.conf.orig	2018-04-29 13:50:21.990525068 +0900
+++ /etc/zabbix/zabbix_agentd.conf	2018-04-29 13:51:57.634012705 +0900
@@ -93,8 +93,7 @@
 # Mandatory: no
 # Default:
 # Server=
-
-Server=127.0.0.1
+Server=192.168.1.114

 ### Option: ListenPort
 #	Agent will listen on this port for connections from the server.
@@ -135,7 +134,7 @@
 # Default:
 # ServerActive=

-ServerActive=127.0.0.1
+ServerActive=192.168.1.114

 ### Option: Hostname
 #	Unique, case sensitive hostname.
@@ -146,7 +145,7 @@
 # Default:
 # Hostname=

-Hostname=Zabbix server
+#Hostname=Zabbix server

 ### Option: HostnameItem
```
```
# firewall-cmd --add-port=10050/tcp --permanent
# firewall-cmd --reload
```
```
# systemctl start zabbix-agent
# systemctl enable zabbix-agent
```
