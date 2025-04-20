#!/bin/zsh

curl -X POST \
  -H "Content-Type: application/json" \
  -d "@./create-user.json" \
  http://localhost:8080/api/v1/user