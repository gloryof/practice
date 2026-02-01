curl -X POST \
  -H "Content-Type: application/json" \
  -d @request.json \
  "http://localhost:8080/api/v1/auth/login" | jq
