#!/bin/zsh

curl -H "Content-Type: application/json" \
    http://localhost:8080/api/users/${USER_ID}/gift/history | jq
