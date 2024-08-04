#!/bin/zsh

curl -H "Content-Type: application/json" \
    -X POST http://localhost:8080/api/users/${USER_ID}/use-gift-point \
    -d @./request/use-gift-point.json -v