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
下記の公式のページのデータを取り込む。

https://github.com/elastic/examples/tree/master/Common%20Data%20Formats/apache_logs#run-example  
データカウントの件数が取得できたらfilebeatは終了して良い。  

データ取り込み用のディレクトリを作成し、データをダウンロードする。
```
cd ${任意のディレクトリ}
wget https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/apache_logs/apache_logs
```

filebeatから取り込みを行う。
```
cd ${fileBeatインストールパス}
./filebeat -e --modules=apache2 --setup -E "setup.template.name=apache-log" -E "setup.template.pattern=apache-log" -E "output.elasticsearch.index=apache-log" -M "apache2.access.var.paths=[${ログダウンロードパス}/apache_logs]"
```
しばらく待機し、下記のパスにアクセスしてcountが1000になっていればOK。  
確認できたらfilebeatを終了する。

## Kibana
### アクセス
下記のURLにアクセス。  
http://localhost:5601/app/kibana

### 設定のインポート
[Management] -> [Kibana] -> [Saved Objects]を表示。  
初期表示されている設定をすべて削除する。  
[Import]を選択し、`/docker/kibana/setting.json`をインポートする。

## Grafana
### アクセス
下記のURLにアクセス。  
http://localhost:3000/login  

初期のログイン/パスワードは admin/admin  

### Data Sourceの設定
Data Sourceには下記の設定を行う。  

|設定項目|値|
|--|--|
|URL|http://elasticsearch:9200|
|Index name|apache-log|

### ダッシュボードの取り込み

1. [Dashboards] -> [Manage]をクリック。
1. [+Import]をクリックする。
1. [Or paste JSON]に `/docker/grafana/setting.json`の中身をコピーする。
1. [Load]をクリックする。
