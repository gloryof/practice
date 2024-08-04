#!/bin/zsh

curl -H "Content-Type: application/json" \
    -X POST http://localhost:8080/api/users/${USER_ID}/change-address \
    -d @./request/change-address.json -v