# 認可サーバ
OAuth2の練習として下記のRFCの仕様を満たすような最低限のものを実装する。
- RFC6749 : The OAuth 2.0 Authorization Framework

練習用のためセキュリティやパフォーマンスといった部分の品質の担保はしない。
## 認可コードフロー
### Authorization
```
curl "http://localhost:8090/api/authorize/code?response_type=code&client_id=own-client&state=xyz&scope=read+write&redirect_uri=https%3A%2F%2Fclient%2Eexample%2Ecom%2Fcb" -v
```
### Token
```
export AUTH_CODE=トークン値;
curl \
-X POST -v \
-H "Authorization:Bearer ${AUTH_CODE}" \
-H "Content-Type: application/json" \
-d '{"grant_type":"authorization_code", "client_id": "own-client","redirect_uri": "https%3A%2F%2Fclient%2Eexample%2Ecom%2Fcb"}' \
"http://localhost:8090/api/token/code"; 
```
## インプリシットフロー
### Authorization
```
curl "http://localhost:8090/api/authorize/implicit?response_type=code&client_id=s6BhdRkqt3&state=xyz&scope=read+write&redirect_uri=https%3A%2F%2Fclient%2Eexample%2Ecom%2Fcb" -v
```
## リソースオーナー・パスワード・クレデンシャルズ
### Authorization
```
curl \
-X POST -v \
-H "Authorization:Basic dGVzdC1vd25lci1pZDp0ZXN0LXBhc3N3b3Jk" \
-H "Content-Type: application/json" \
-d '{"grant_type":"password", "scope": "read write","redirect_uri": "https%3A%2F%2Fclient%2Eexample%2Ecom%2Fcb"}' \
"http://localhost:8090/api/token/owner"
```
## クライアント・クレデンシャルズ
### Authorization
```
curl \
-X POST -v \
-H "Authorization:Basic dGVzdC1jbGllbnQtaWQ6dGVzdC1wYXNzd29yZA==" \
-H "Content-Type: application/json" \
-d '{"grant_type":"client_credentials", "scope": "read write","redirect_uri": "https%3A%2F%2Fclient%2Eexample%2Ecom%2Fcb"}' \
"http://localhost:8090/api/token/client"
```
## 共通エンドポイント
### リフレッシュトークン
```
curl \
-X POST -v \
-H "Content-Type: application/json" \
-d '{"grant_type":"refresh_token", "refresh_token": リフレッシュトークン値, "scope": "read write"}' \
"http://localhost:8090/api/token/refresh"
```
### Introspection
```
curl \
-X POST -v \
-H "Authorization:Basic czZCaGRSa3F0MzpnWDFmQmF0M2JW" \
-H "Content-Type: application/json" \
-d '{"token": トークン値, "token_type_hint": "type-hint"}' \
"http://localhost:8090/api/introspect"
```