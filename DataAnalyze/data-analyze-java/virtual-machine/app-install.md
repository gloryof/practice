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

## Filebeatのインストール
参考：[公式のリポジトリインストール方法ページ](https://www.elastic.co/guide/en/beats/filebeat/current/setup-repositories.html)

```
# rpm --import https://packages.elastic.co/GPG-KEY-elasticsearch
# vi /etc/yum.repos.d/elasticsearch.repo
# yum install filebeat
```

[/etc/yum.repos.d/elasticsearch.repo]

```
[elasticsearch-6.x]
name=Elasticsearch repository for 6.x packages
baseurl=https://artifacts.elastic.co/packages/6.x/yum
gpgcheck=1
gpgkey=https://artifacts.elastic.co/GPG-KEY-elasticsearch
enabled=1
autorefresh=1
type=rpm-md
```