# 認可サーバ
## 認可コードフロー
### Authorization
```
curl "http://localhost:8080/api/authorize/code?response_type=code&client_id=s6BhdRkqt3&state=xyz&redirect_uri=https%3A%2F%2Fclient%2Eexample%2Ecom%2Fcb"
```
### Token
```
curl \
-X POST -v \
-H "Authorization:Bearer f9420d41-295a-496f-bbdc-a8e1e2b1a939" \
-H "Content-Type: application/json" \
-d '{"grantType":"authorizationCode", "clientId": "SplxlOBeZQQYbYS6WxSbIA","redirectUrl": "https%3A%2F%2Fclient%2Eexample%2Ecom%2Fcb"}' \
"http://localhost:8080/api/token/code" 
```