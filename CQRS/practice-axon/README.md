# Axonを使ったCQRSの練習

## 事前準備
### Axon Server
https://docs.axoniq.io/reference-guide/axon-server/installation/local-installation

## API
### Create 
```
curl -H "Content-Type: application/json" \
    -X POST http://localhost:8080/api/users \
    -d @./request/create-user.json
```

### Get User
```
curl -H "Content-Type: application/json" \
    http://localhost:8080/api/users/test-user-id | jq
```

### Change name
```
curl -H "Content-Type: application/json" \
    -X POST http://localhost:8080/api/users/test-user-id/change-name \
    -d @./request/change-name.json
```

### Change address
```
curl -H "Content-Type: application/json" \
    -X POST http://localhost:8080/api/users/test-user-id/change-address \
    -d @./request/change-address.json
```

### Charge gift point
```
curl -H "Content-Type: application/json" \
    -X POST http://localhost:8080/api/users/test-user-id/charge-gift-point \
    -d @./request/charge-gift-point.json
```

### Use gift point
```
curl -H "Content-Type: application/json" \
    -X POST http://localhost:8080/api/users/test-user-id/use-gift-point \
    -d @./request/use-gift-point.json
```