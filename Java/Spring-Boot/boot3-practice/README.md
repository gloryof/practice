# Spring Boot3の練習場

## TODO
- 認証周りのエラーがレスポンスが帰ってきていないので直す
- トークンがない場合の認証エラーがBasic扱いになっているので直す
- Actuatorの内容を調べる
- OpenMetrics/OpenTracingを調べる

## API
### Register API
#### Create
```
curl -v \
    -X POST \
    -H 'Content-Type:application/json' \
    -d @request/user.json \
    http://localhost:8080/api/register
```
### Authenticate API
#### Generate token
```
curl -v \
    -X POST \
    -H 'Content-Type:application/json' \
    -d @request/auth.json \
    http://localhost:8080/api/auth
```
### User API
#### Get All
```
curl -v \
    -H "Authorization:Bearer ${TOKEN}"  \
    http://localhost:8080/api/users
```
#### Get User
```
curl -v \
    -H "Authorization:Bearer ${TOKEN}"  \
    http://localhost:8080/api/users/test-user-id
```