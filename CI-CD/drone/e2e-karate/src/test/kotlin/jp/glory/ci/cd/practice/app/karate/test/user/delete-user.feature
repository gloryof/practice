Feature: Register user

  Background:
    * def auth = call read(authFeaturePath)
    * def registerData = read(testFileLocation + "/user/register-user.json")

  Scenario: Success delete user
    Given url targetHost + "/user"
    And request registerData
    And header X-CSRF-TOKEN = auth.csrfToken
    And header Authorization = auth.authToken
    When method POST
    Then status 201
    And def testUserId = response.userId

    Given url targetHost + "/user/" + testUserId
    And header X-CSRF-TOKEN = auth.csrfToken
    And header Authorization = auth.authToken
    When method DELETE
    Then status 204

    Given url targetHost + "/user/" + testUserId
    And header X-CSRF-TOKEN = auth.csrfToken
    And header Authorization = auth.authToken
    When method GET
    Then status 404

  Scenario: Not found
    Given url targetHost + "/user/not-found-user"
    And header X-CSRF-TOKEN = auth.csrfToken
    And header Authorization = auth.authToken
    When method DELETE
    Then status 404

