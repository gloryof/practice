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
