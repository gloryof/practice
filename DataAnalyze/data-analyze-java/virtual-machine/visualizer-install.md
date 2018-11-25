## Kibanaのインストール

参考：[公式のRPMインストール方法ページ](https://www.elastic.co/guide/en/kibana/current/rpm.html)

```
# rpm --import https://artifacts.elastic.co/GPG-KEY-elasticsearch
# vi /etc/yum.repos.d/kibana.repo
# sudo yum install kibana
```

[/etc/yum.repos.d/kibana.repo]

```
[kibana-6.x]
name=Kibana repository for 6.x packages
baseurl=https://artifacts.elastic.co/packages/6.x/yum
gpgcheck=1
gpgkey=https://artifacts.elastic.co/GPG-KEY-elasticsearch
enabled=1
autorefresh=1
type=rpm-md
```

## Kiabanaの設定

[/etc/kibana/kibana.yml]

```
server.host: "192.168.1.119"
elasticsearch.url: "http://192.168.1.118:9200"
```