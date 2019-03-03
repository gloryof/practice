## Javaをインストール
ホストOSでLinux用のOpenJDK11.をダウンロード。  
ゲストOS内の `/opt/jdk-11.0` に配置。  

## アプリを配置
```
# cd /var/lib/
# mkdir monitoring-app
# chown app:app monitoring-app/
```
ビルドしたjarファイルを `/var/lib/monitoring-app/app.jar` として配置。  
`conf/conf.yml` を `/var/lib/monitoring-app/conf.yml`  に配置。  
`conf/app.env` を `/var/lib/monitoring-app/app.env`  に配置。  

## firewalldの設定
```
# firewall-cmd --permanent --new-service=monitoring-app

# firewall-cmd --permanent --service=monitoring-app --set-short=monitoring-app
# firewall-cmd --permanent --service=monitoring-app --set-description=monitoring-app
# firewall-cmd --permanent --service=monitoring-app --add-port=8080/tcp
# firewall-cmd --add-port=50080/tcp --zone=public --service=monitoring-app --permanent

# firewall-cmd --permanent --add-service=monitoring-app --zone=public

# firewall-cmd --reload
```

## systemd設定
`/etc/systemd/system/monitoring-app.service` に  
`conf/monitoring-app.service`　の内容を配置。

```
# systemctl daemon-reload
# systemctl start monitoring-app.service
# systemctl enable monitoring-app.service
```

## Zabbix-Agentのインストール
参考サイト：https://blog.apar.jp/zabbix/9559/
```
# rpm -ivh https://repo.zabbix.com/zabbix/4.0/rhel/7/x86_64/zabbix-release-4.0-1.el7.noarch.rpm
# yum install zabbix-agent
```
zabbix_agentd.confの設定。
```
]# diff -u /etc/zabbix/zabbix_agentd.conf{.orig,}
--- /etc/zabbix/zabbix_agentd.conf.orig	2019-03-03 15:33:44.726920084 +0900
+++ /etc/zabbix/zabbix_agentd.conf	2019-03-03 15:35:06.528433282 +0900
@@ -95,7 +95,7 @@
 # Default:
 # Server=
 
-Server=127.0.0.1
+Server=192.168.1.114
 
 ### Option: ListenPort
 #	Agent will listen on this port for connections from the server.
@@ -136,7 +136,7 @@
 # Default:
 # ServerActive=
 
-ServerActive=127.0.0.1
+ServerActive=192.168.1.114
 
 ### Option: Hostname
 #	Unique, case sensitive hostname.
@@ -147,7 +147,7 @@
 # Default:
 # Hostname=
 
-Hostname=Zabbix server
+#Hostname=Zabbix server
 
 ### Option: HostnameItem
 #	Item used for generating Hostname if it is undefined. Ignored if Hostname is defined.
```
firewalldの設定。

```
# firewall-cmd --add-port=10050/tcp --permanent
# firewall-cmd --reload
```
zabbix-agentを起動。

```
# systemctl start zabbix-agent
# systemctl enable zabbix-agent
```
