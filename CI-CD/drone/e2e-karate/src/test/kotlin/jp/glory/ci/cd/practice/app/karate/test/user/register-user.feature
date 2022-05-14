Feature: Register user

  Background:
    * def auth = call read(authFeaturePath)
    * def registerData = read(testFileLocation + "/user/register-user.json")
    * def badRequestData = read(testFileLocation + "/user/register-user-bad-request.json")

  Scenario: Success register user
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
    When method GET
    Then status 200
    And match testUserId == response.userId
    And match registerData.givenName == response.givenName
    And match registerData.familyName == response.familyName

  Scenario: Bad request
    Given url targetHost + "/user"
    And request badRequestData
    And header X-CSRF-TOKEN = auth.csrfToken
    And header Authorization = auth.authToken
    When method POST
    Then status 400

