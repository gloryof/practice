curl -H "Content-Type: application/json" \
  -H "Authorization: Bearer invalid" \
  "http://localhost:8080/api/v1/user" | jq
