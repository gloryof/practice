# dev
## ConfigサーバとDBサーバの起動
下記のコマンドを実行し、ConfigサーバとDBサーバを起動する。  

```
docker-compose up -d config-server dev-db stage-db
```

下記のURLにアクセスしてConfigサーバが起動していることを確認する。
- http://localhost:8888/config_app/dev
- http://localhost:8888/config_app/stage

## Appサーバの起動
下記のコマンドを実行し、Appサーバを起動する。  

```
docker-compose up -d dev-app stage-app  
```


下記のURLにアクセスしてConfigサーバが起動していることを確認する。  
（起動するまでに10秒〜20秒ほど待つ必要がある）  
- http://localhost:8081/api/config
- http://localhost:8081/api/db
- http://localhost:8082/api/config
- http://localhost:8082/api/db
