## 実行方法
```
cd docker
docker-compose up -d
```
## データ投入
### acountsデータ  
公式ドキュメントのサンプル（accounts.zip）を使う。  
https://www.elastic.co/guide/en/kibana/current/tutorial-load-dataset.html   

```
curl -H 'Content-Type: application/x-ndjson' -XPOST 'localhost:9200/bank/account/_bulk?pretty' --data-binary @accounts.json
```

### apacheログデータ
下記の公式のexampleデータを使う。  
https://github.com/elastic/examples/tree/master/Common%20Data%20Formats/apache_logs  
（2015-05-17 00:00:00.000〜2015-05-21 12:00:00.000のデータとなっている）


```
wget https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/apache_logs/apache_logs
```

#### filebeatのインストール
```
curl -L -O https://artifacts.elastic.co/downloads/beats/filebeat/filebeat-6.5.4-darwin-x86_64.tar.gz
tar xzvf filebeat-6.5.4-darwin-x86_64.tar.gz
```

##### データの取り込み
下記の公式のページの手順に従ってデータを取り込む。

https://github.com/elastic/examples/tree/master/Common%20Data%20Formats/apache_logs#run-example  
データカウントの件数が取得できたらfilebeatは終了して良い。 

## Kibana
### アクセス
下記のURLにアクセス。  
http://localhost:5601/app/kibana

### 設定のインポート
[Management] -> [Kibana] -> [Saved Objects]を表示。  
[Import]を選択し、`/docker/kibana/setting.json`をインポートする。