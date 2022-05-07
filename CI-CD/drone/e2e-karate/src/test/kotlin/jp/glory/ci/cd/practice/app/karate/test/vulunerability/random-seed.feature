Feature: Random seed

  Background:
    * def authFeaturePath = 'classpath:jp/glory/ci/cd/practice/app/karate/test/auth/auth.feature'
    * def authFeatureParam = { userId: 'test-system-user-id', password: 'test-system-password' }
    * def auth = call read(authFeaturePath) authFeatureParam

  Scenario: Success return number
    Given url targetHost + "/vulnerability/random-seed"
    And header X-CSRF-TOKEN = auth.csrfToken
    And header Authorization = auth.authToken
    When method GET
    Then status 200
