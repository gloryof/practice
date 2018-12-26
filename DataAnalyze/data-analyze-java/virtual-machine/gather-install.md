## Javaのインストール
```
# yum install java-1.8.0
```

## Logstashのインストール
参考：[公式のリポジトリインストール方法ページ](https://www.elastic.co/guide/en/logstash/current/installing-logstash.html)  

```
# rpm --import https://artifacts.elastic.co/GPG-KEY-elasticsearch
# vi /etc/yum.repos.d/logstash.repo
# yum install logstash
# systemctl enable logstash.service
```

