# Spring Boot3の練習場

## API
### User API
#### Get All
```
curl -v http://localhost:8080/api/users
```
#### Create
```
curl -v \
    -X POST \
    -H 'Content-Type:application/json' \
    -d @request/user.json \
    http://localhost:8080/api/users
```