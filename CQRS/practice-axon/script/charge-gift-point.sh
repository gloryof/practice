#!/bin/zsh

curl -H "Content-Type: application/json" \
    -X POST http://localhost:8080/api/users/${USER_ID}/charge-gift-point \
    -d @./request/charge-gift-point.json -v