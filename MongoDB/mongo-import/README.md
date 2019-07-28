# mongoimport
## 概要
mongoimporを試す用の練習場所。
## データ
下記のサイトで提供されているデータを使う。  
http://insideairbnb.com/get-the-data.html
### 取り込み方法
CSVデータをダウンロードする。
```
$ curl http://data.insideairbnb.com/japan/kant%C5%8D/tokyo/2019-06-27/data/calendar.csv.gz -O
$ gunzip calendar.csv.gz
```
calendarのインポート。
```
mongoimport \
    --db single-db \
    --username test-user \
    --password test-password \
    --host 127.0.0.1:27017 \
    --collection calendar \
    --type csv \
    --file /tmp/mongo-data/calendar.csv \
    --columnsHaveTypes \
    --parseGrace skipRow \
    --fieldFile ./single-node/docker/mongodb/csv-fields/calendar.txt
```

### ログイン
```
$ mongo localhost/single-db -u test-user -p test-password
```