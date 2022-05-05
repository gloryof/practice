# Input
#   userId: Login user ID
#   password: Login user password
#
# Output
#   csrfToken: CSRF token
#   authToken: Authentication token as Bearer format.
Feature: Call authentication API

  Scenario: Success return number
    Given url "http://localhost:8080/csrf/token"
    When method post
    Then status 200
    And def csrfToken = response

    Given url "http://localhost:8080/authenticate"
    And header X-CSRF-TOKEN = csrfToken
    And request { userId: "#(userId)", password: "#(password)" }
    When method post
    Then status 200
    And def authToken = "Bearer " + response.tokenValue
