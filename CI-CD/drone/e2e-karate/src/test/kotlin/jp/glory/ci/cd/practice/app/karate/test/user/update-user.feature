Feature: Register user

  Background:
    * def auth = call read(authFeaturePath)
    * def updateData = read(testFileLocation + "/user/update-user.json")
    * def badRequestData = read(testFileLocation + "/user/update-user-bad-request.json")

  Scenario: Success update user
    Given url targetHost + "/user/" + updateUserId
    And request updateData
    And header X-CSRF-TOKEN = auth.csrfToken
    And header Authorization = auth.authToken
    When method PUT
    Then status 200

    Given url targetHost + "/user/" + updateUserId
    And header X-CSRF-TOKEN = auth.csrfToken
    And header Authorization = auth.authToken
    When method GET
    Then status 200
    And match updateUserId == response.userId
    And match updateData.givenName == response.givenName
    And match updateData.familyName == response.familyName

  Scenario: Bad request
    Given url targetHost + "/user/" + updateUserId
    And request badRequestData
    And header X-CSRF-TOKEN = auth.csrfToken
    And header Authorization = auth.authToken
    When method PUT
    Then status 400

