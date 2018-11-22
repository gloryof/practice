## Javaのインストール
```
# mkdir /usr/lib/java

# cd /usr/lib/java
# curl https://download.java.net/java/GA/jdk11/13/GPL/openjdk-11.0.1_linux-x64_bin.tar.gz -O
# tar zxvf openjdk-11.0.1_linux-x64_bin.tar.gz 

# ln -s jdk-11.0.1/ current
```

## アプリの配置
```
# mkdir /usr/lib/data-analyze/config

# mv /tmp/data-analyze-java-0.0.1-SNAPSHOT.jar /usr/lib/data-analyze/data-analyze.jar
# vi /usr/lib/data-analyze/conf/logger.xml

# chown -R app:app /usr/lib/data-analyze

# vi /etc/systemd/system/data-analyze.service
```

## ファイアウォールの設定
```
# firewall-cmd --permanent --new-service=data-analyze

# firewall-cmd --permanent --service=data-analyze --set-short=data-analyze
# firewall-cmd --permanent --service=data-analyze --set-description=data-analyze
# firewall-cmd --permanent --service=data-analyze --add-port=8080/tcp

# firewall-cmd --permanent --add-service=data-analyze --zone=public

# firewall-cmd --reload
```