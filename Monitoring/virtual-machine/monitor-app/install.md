## Javaをインストール
ホストOSでLinux用のOpenJDK9をダウンロード。  
ゲストOS内の `/opt/jdk-9.0` に配置。  

## アプリを配置
```
# cd /var/lib/
# mkdir monitoring-app
# chown app:app monitoring-app/
```
ビルドしたjarファイルを `/var/lib/monitoring-app/app.jar` として配置。  
`conf/conf.yml` を `/var/lib/monitoring-app/conf.yml`  に配置。

## firewalldの設定
```
# firewall-cmd --permanent --new-service=monitoring-app

# firewall-cmd --permanent --service=monitoring-app --set-short=monitoring-app
# firewall-cmd --permanent --service=monitoring-app --set-description=monitoring-app
# firewall-cmd --permanent --service=monitoring-app --add-port=8080/tcp

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
