# フルスタック練習

## 概要
フロントとサーバの連携の練習をするところ。  
本来の意味のフルスタックじゃないけど、名前が思いつかないのでフルスタックとしている。

### サーバ
DBをやってもいいけど慣れているので今回の練習対象外。


#### APIの実行方法
#### ユーザ作成
```
curl \
    -H "Content-Type:application/json" \
    -X POST \
    -d "{\"name\": \"test-uesr\", \"birthday\": \"1986-12-16\", \"password\":\"test-password\"}" \
    http://localhost:8080/api/user/register
```

#### ログイン
```
curl \
    -H "Content-Type:application/json" \
    -X POST \
    -d "{\"userId\": \"${USER_ID}\", \"password\":\"test-password\"}" \
    http://localhost:8080/api/auth/login
```