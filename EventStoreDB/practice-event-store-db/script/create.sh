#!/bin/zsh

curl -H "Content-Type: application/json" \
    -X POST http://localhost:8080/api/users \
    -d @./request/create-user.json