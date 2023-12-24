# grpcの練習

## 事前準備
grpcurlをインストールする。

```
brew install grpcurl
```

## 実行方法
### ユーザ登録
```
grpcurl -plaintext -d '{ "user_name": "test-user", "birthday": "1986-12-16" }' localhost:6565 user.RegisterUserService.Register
```

### ユーザ更新
```
grpcurl -plaintext -d '{ "user_id": "5938d340-b946-4f92-9f98-d756a1c5c112", "user_name": "update-user", "birthday": "1901-01-01" }' localhost:6565 user.UpdateUserService.Update
```

### ユーザ取得
```
grpcurl -plaintext -d '{ "user_id": "5938d340-b946-4f92-9f98-d756a1c5c112" }' localhost:6565 user.GetUserService.GetUserById
```