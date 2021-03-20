# Client
## URL
### Authorization Code
- http://localhost:8100/code/user/view
- http://localhost:8100/code/authorized?code=1234&state=test
### Implicit
- http://localhost:8100/implicit/user/view
- http://localhost:8100/implicit/authorized?access_token=1234&state=test&token_type=implicit&expires_in=3600&user_id=test-user_id&scope=READ
### Owner
- http://localhost:8100/owner/login
### Client
- http://localhost:8100/client/login