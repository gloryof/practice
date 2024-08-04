# EventStoreDBを使った練習

## API
### Create 
```
curl -H "Content-Type: application/json" \
    -X POST http://localhost:8080/api/users \
    -d @./request/create-user.json
```

or 

```
cd script
./create.sh
```
### Get User
```
curl -H "Content-Type: application/json" \
    http://localhost:8080/api/users/test-user-id | jq
```

or

```
cd script
export USER_ID=test-user-id
./get-user.sh
```

### Change name
```
curl -H "Content-Type: application/json" \
    -X POST http://localhost:8080/api/users/test-user-id/change-name \
    -d @./request/change-name.json
```

or

```
cd script
export USER_ID=test-user-id
./change-name.sh
```

### Change address
```
curl -H "Content-Type: application/json" \
    -X POST http://localhost:8080/api/users/test-user-id/change-address \
    -d @./request/change-address.json
```

or

```
cd script
export USER_ID=test-user-id
./change-address.sh
```

### Charge gift point
```
curl -H "Content-Type: application/json" \
    -X POST http://localhost:8080/api/users/test-user-id/charge-gift-point \
    -d @./request/charge-gift-point.json
```

or

```
cd script
export USER_ID=test-user-id
./charge-gift-point.sh
```

### Use gift point
```
curl -H "Content-Type: application/json" \
    -X POST http://localhost:8080/api/users/test-user-id/use-gift-point \
    -d @./request/use-gift-point.json
```

or

```
cd script
export USER_ID=test-user-id
./use-gift-point.sh
```