# PostgreSQLのスケールの練習
## 目的
PostgreSQLを使ったときのスケールの方法を学ぶ。

## データの登録方法
```
curl -X POST http://localhost:8080/api/detail -d '{"name": "test"}' -H "Content-Type: application/json"
```