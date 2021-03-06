## 実行方法
コンテナの数が多いので全て一気に起動すると正常に動かない場合がある。  
基本的にはスクリプト経由で実行すること。  

### ビルド手順
`docker` ディレクトリで下記のコマンドを実行する。
```
./build.sh
```

### スクリプト実行
`${script} start`で起動し、`${script} stop`で停止する。  
対応するscriptは下記。  

|script名|概要|
|-------|----|
|data-container.sh|他のコンテナから参照するデータ群。ElasticsearchとPostgreSQLが含まれる。|
|kibana-container.sh|Kibanaコンテナ。|
|grafana-container.sh|Grafanaコンテナ。|
|metabase-container.sh|Metabaseコンテナ。|
|redash-container.sh|Redashの起動に必要なコンテナ群。|

基本的には`data-container.sh`を実行し、  
必要なデータ群が動作してから起動するのが望ましい。


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

#### Elasticsearch
|設定項目|値|
|--|--|
|URL|http://elasticsearch:9200|
|Index name|apache-log|
|Version|6.0+|

#### PostgreSQL
|設定項目|値|
|--|--|
|Host|postgres:5432|
|Database|analyze-database|
|User|analyze-user|
|Password|analyze-password|
|SSL Mode|disable|
|Version|10|

### ダッシュボードの取り込み
下記の`対象データ`ごとにデータをインポートする。  
インポートする方法は`手順`を参照。
#### 対象データ
- `/docker/grafana/log-analyze-setting.json`
- `/docker/grafana/db-log-analyze-setting.json`
#### 手順
1. [Dashboards] -> [Manage]をクリック。
1. [+Import]をクリックする。
1. [Or paste JSON]に対象のJSONの中身をコピーする。
1. [Load]をクリックする。

## Metabase
### アクセス
下記のURLにアクセス。  
http://localhost:3001

### DB設定
下記を設定する。  
|設定項目|値|
|--|--|
|名前|DBLog|
|ホスト|postgres|
|ポート|5432|
|データベース名|analyze-database|
|データベースユーザ名|analyze-user|
|データベースパスワード|analyze-password|

## Redash
### アクセス
下記のURLにアクセス。  
http://localhost:5000/  

### DB設定
下記を設定する。  
|設定項目|値|
|--|--|
|Name|DBLog|
|Host|postgres|
|Port|5432|
|User|analyze-user|
|Password|analyze-password|
|SSL Mode|prefer|
|Database Name|analyze-database|
