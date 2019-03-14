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
`http://localhost:9090/graph`