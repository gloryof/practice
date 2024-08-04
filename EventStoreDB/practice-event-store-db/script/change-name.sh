#!/bin/zsh

curl -H "Content-Type: application/json" \
    -X POST http://localhost:8080/api/users/${USER_ID}/change-name \
    -d @./request/change-name.json -v