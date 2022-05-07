Feature: Get user

  Background:
    * def auth = call read(authFeaturePath)
    * def expected = read(testFileLocation + "/user/system-user.json")

  Scenario: Success return user
    Given url targetHost + "/user/" + testUserId
    And header X-CSRF-TOKEN = auth.csrfToken
    And header Authorization = auth.authToken
    When method GET
    Then status 200
    And match expected == response
