## Javaのインストール
```
# mkdir /usr/lib/java

# cd /usr/lib/java
# curl https://download.java.net/java/GA/jdk11/13/GPL/openjdk-11.0.1_linux-x64_bin.tar.gz -O
# tar zxvf openjdk-11.0.1_linux-x64_bin.tar.gz 

# ln -s jdk-11.0.1/ current
```

## Elasticsearchのインストール
参考：[公式のRPMインストール方法ページ](https://www.elastic.co/guide/en/elasticsearch/reference/current/rpm.html)

```
# rpm --import https://artifacts.elastic.co/GPG-KEY-elasticsearch
# vi /etc/yum.repos.d/elasticsearch.repo
# export JAVA_HOME=/usr/lib/java/current
# yum install elasticsearch

# vi /etc/systemd/system/elasticsearch.service.d/override.conf
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

[/etc/systemd/system/elasticsearch.service.d/override.conf]

```
[Service]
Environment='JAVA_HOME=/usr/lib/java/current'
LimitMEMLOCK=infinity
```

## Elasticseach関連の設定
[/etc/elasticsearch/elasticsearch.yml]

```
network.host: _site_
bootstrap.memory_lock: true
```

[/etc/elasticsearch/jvm.options]

```
-Des.enforce.bootstrap.checks=true
```