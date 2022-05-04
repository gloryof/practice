Feature: Random seed

  Scenario: Success return number
    Given url "http://localhost:8080/csrf/token"
    When method post
    Then status 200
    And def csrfToken = response

    Given url "http://localhost:8080/authenticate"
    And header X-CSRF-TOKEN = csrfToken
    And request { userId: "test-system-user-id", password: "test-system-password" }
    When method post
    Then status 200
    And def authToken = response.tokenValue

    Given url "http://localhost:8080/vulnerability/random-seed"
    And header X-CSRF-TOKEN = csrfToken
    And header Authorization = "Bearer " + authToken
    When method GET
    Then status 200




