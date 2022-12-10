# Spring Boot3の練習場

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