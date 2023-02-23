# InfluxDBの練習場

## 実行方法
### 事前準備 
```
mkdir generate-data
```

### iostatの取得
```
./script/import.sh &{秒数}
```

### CSVのアップロード
```
./script/upload.sh
```

### 結果の取得
```
./script/get-result.sh
```


# TODO
- MetricsをInfluxDBに繋ぐ
  - https://spring.pleiades.io/spring-boot/docs/current/reference/html/actuator.html#actuator.metrics.export.influx
