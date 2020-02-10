FROM openjdk:11-jre-slim AS exec-stage
COPY artifact/java-app.jar /java-app.jar
COPY artifact/config.yml /config.yml
# JenkinsのバージョンだとBUILDKIT未対応なのでコピーで対応（ver:17.09.1-ce）
COPY artifact/secret.yml /secret.yml
EXPOSE 8080
CMD ["java", "-jar", "java-app.jar", "--param.value='The param Value'", "--spring.config.additional-location=file:/secret.yml,file:/config.yml"]