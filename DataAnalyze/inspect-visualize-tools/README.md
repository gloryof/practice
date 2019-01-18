## 実行方法
```
cd docker
docker-compose up -d
```
## データ投入
公式ドキュメントのサンプル（accounts.zip）を使う。  
https://www.elastic.co/guide/en/kibana/current/tutorial-load-dataset.html   

```
curl -H 'Content-Type: application/x-ndjson' -XPOST 'localhost:9200/bank/account/_bulk?pretty' --data-binary @accounts.json
```

## アクセス
下記のURLにアクセス。  
http://localhost:5601/app/kibana