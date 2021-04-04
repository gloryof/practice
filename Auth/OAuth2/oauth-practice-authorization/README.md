# 認可サーバ
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
-H "Authorization:Basic czZCaGRSa3F0MzpnWDFmQmF0M2JW" \
-H "Content-Type: application/json" \
-d '{"grant_type":"password", "scope": "read write","redirect_uri": "https%3A%2F%2Fclient%2Eexample%2Ecom%2Fcb"}' \
"http://localhost:8090/api/token/owner"
```
## クライアント・クレデンシャルズ
### Authorization
```
curl \
-X POST -v \
-H "Authorization:Basic czZCaGRSa3F0MzpnWDFmQmF0M2JW" \
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
-d '{"grant_type":"refresh", "refresh_token": "f9420d41-295a-496f-bbdc-a8e1e2b1a939", "scope": "read write"}' \
"http://localhost:8090/api/token/refresh"
```
### Introspection
```
curl \
-X POST -v \
-H "Authorization:Basic czZCaGRSa3F0MzpnWDFmQmF0M2JW" \
-H "Content-Type: application/json" \
-d '{"token": "f9420d41-295a-496f-bbdc-a8e1e2b1a939", "token_type_hint": "type-hint"}' \
"http://localhost:8090/api/introspect"
```