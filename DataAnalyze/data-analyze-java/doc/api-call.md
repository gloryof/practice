[ログAPI]
```
 curl 'http://localhost:8080/log?result=Great&status=400'
```

[検索API]
```
curl -d '{"name":"テスト名前","age":20, "hobby": {"name": "テスト趣味", "ranking": 10, "type": "Sports"} }' -H 'Content-Type: application/json' 'http://localhost:8080/search'
```
