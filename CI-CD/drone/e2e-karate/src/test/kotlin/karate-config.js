function fn() {
  var getEnv = function(keyName, defaultValue) {
    var value = java.lang.System.getenv(keyName);
    if (value == null || value == "") {
        return defaultValue;
    }
    return value;
  };
  var targetUrl = getEnv('TARGET_URL', "http://localhost");
  var targetPort = getEnv("TARGET_PORT", "8080");

  var config = {
    targetHost: targetUrl + ":" + targetPort,
    loginUserId: getEnv("USER_ID", "test-system-user-id"),
    loginPassword: getEnv("PASSWORD", "test-system-password"),
    testUserId: getEnv("USER_ID", "test-system-user-id"),
    updateUserId: getEnv("UPDATE_USER_ID", "test-for-update-user-id"),
    authFeaturePath: "classpath:jp/glory/ci/cd/practice/app/karate/test/auth/auth.feature"
  };

  return config;
}
