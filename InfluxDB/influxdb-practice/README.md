# InfluxDBの練習場

## 実行方法
### 事前準備 
```
mkdir generate-data
```

### InfluxDB
#### 起動
```
./script/start.sh
```

http://localhost:30086/signin
#### 停止
```
./script/stop.sh
```


### APIの実行
#### CSVのアップロード
```
./script/upload.sh
```

#### 結果の取得
Postmanを使い `config/postman/config.json` をインポートする。

# TODO
- MetricsをInfluxDBに繋ぐ
  - https://spring.pleiades.io/spring-boot/docs/current/reference/html/actuator.html#actuator.metrics.export.influx
