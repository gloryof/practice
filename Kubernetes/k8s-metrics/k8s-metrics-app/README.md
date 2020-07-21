# k8sメトリクス練習用アプリ
k8sにのメトリクスを試すためのアプリ。

## 実行方法
### 登録
```
$ curl -X POST http://localhost:8080/api/person \
    -H "Content-Type: application/json" \
    -d '{"name": "test", "age": 34}'
```
### 更新
```
$ curl -X PUT http://localhost:8080/api/person/${personId} \
    -H "Content-Type: application/json" \
    -d '{"name": "modified", "age": 49}'
```

### 取得
```
$ curl http://localhost:8080/api/person/${personId}
```