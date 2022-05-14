# Input
#   userId: Login user ID
#   password: Login user password
#
# Output
#   csrfToken: CSRF token
#   authToken: Authentication token as Bearer format.
@ignore
Feature: Call authentication API

  Scenario: Success return number
    Given url targetHost + "/csrf/token"
    When method post
    Then status 200
    And def csrfToken = response

    Given url targetHost + "/authenticate"
    And header X-CSRF-TOKEN = csrfToken
    And request { userId: "#(loginUserId)", password: "#(loginPassword)" }
    When method post
    Then status 200
    And def authToken = "Bearer " + response.tokenValue
