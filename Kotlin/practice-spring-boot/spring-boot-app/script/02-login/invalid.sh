curl -X POST \
  -H "Content-Type: application/json" \
  -d @invalid-request.json \
  "http://localhost:8080/api/v1/auth/login" | jq
