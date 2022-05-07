function fn() {
  var config = {
    targetHost: "http://localhost:8080"
  };

  if (karate.env == "ci") {
    config.targetHost = "http://target:8080"
  };
  return config;
}
