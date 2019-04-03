# 実行方法

## ビルド手順
### アプリのビルド
アプリをビルドするためにはjarファイルが必要となる。  
`monitoring-app`ディレクトリ配下に移動し下記のコマンドを実行する。  

```
$ ./mvnw package
$  cp target/monitoring-app-0.0.1-SNAPSHOT.jar ../docker-prometheus/docker/app/build/monitoring-app.jar
```

`docker-prometheus/docker`ディレクトリに戻り下記のコマンドを実行する。

```
$ docker-compose build
```

### 起動
`docker-prometheus/docker`ディレクトリで下記のコマンドを実行する。

```
$ docker-compose up -d
```

### アクセスURL
#### アプリケーション
下記のどちらかのURLにアクセスする。

- http://localhost/users/all
- http://localhost/users/limit

#### Prometheus
下記のURLにアクセスする。  
http://localhost:9090/graph

#### Grafana
下記のURLにアクセスする。  
http://localhost:3000/login  

初期のログイン/パスワードは admin/admin

##### データソースの設定

|設定項目|値|
|--|--|
|URL|http://mon-prometheus:9090|

##### ダッシュボードの設定
下記のURLで`docker/grafana/setting`内にあるJSONをインポートする。
http://localhost:3000/dashboard/import

### アラートの出し方
DBのコンテナを停止する。
```
$ docker-compose stop mon-pro-db
```
アラート受け取りを受け取るサーバのログを監視する。
```
$ docker-compose logs -f mon-pro-reciever
```

[実行例]
```
$ docker-compose logs -f mon-pro-reciever
Attaching to mon-pro-reciever
mon-pro-reciever    | {"receiver":"alert-reciever","status":"firing","alerts":[{"status":"firing","labels":{"alertname":"staus-ng","instance":"http://mon-pro-web/users/limit","job":"service-metrics"},"annotations":{},"startsAt":"2019-04-03T22:54:43.878437234+09:00","endsAt":"0001-01-01T00:00:00Z","generatorURL":"http://666c70d0aacb:9090/graph?g0.expr=probe_success%7Binstance%3D%22http%3A%2F%2Fmon-pro-web%2Fusers%2Flimit%22%2Cjob%3D%22service-metrics%22%7D+%3D%3D+0\u0026g0.tab=1"}],"groupLabels":{},"commonLabels":{"alertname":"staus-ng","instance":"http://mon-pro-web/users/limit","job":"service-metrics"},"commonAnnotations":{},"externalURL":"http://4c6958f3d599:9093","version":"4","groupKey":"{}:{}"}
mon-pro-reciever    |
```