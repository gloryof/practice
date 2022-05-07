Feature: Random seed

  Background:
    * def auth = call read(authFeaturePath)

  Scenario: Success return number
    Given url targetHost + "/vulnerability/random-seed"
    And header X-CSRF-TOKEN = auth.csrfToken
    And header Authorization = auth.authToken
    When method GET
    Then status 200
