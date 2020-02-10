# syntax = docker/dockerfile:experimental
FROM maven:3.6.3-jdk-11 AS build-stage
WORKDIR /app
RUN apt-get install git
RUN git clone --no-checkout https://github.com/gloryof/practice.git \
    && cd practice \
    && git config core.sparsecheckout true \
    && touch .git/info/sparse-checkout \
    && echo "container" > .git/info/sparse-checkout \
    && git checkout

RUN mvn clean package -f practice/container/container-java/java-app/pom.xml

FROM openjdk:11-jre-slim AS exec-stage
COPY --from=build-stage \
    /app/practice/container/container-java/java-app/target/java-app-0.0.1-SNAPSHOT.jar \
    /java-app.jar
COPY --from=build-stage \
    /app/practice/container/container-java/deploy/conf/app/config.yml \
    /config.yml
RUN --mount=type=secret,id=secret-config,dst=/tmp/secret.yml cp /tmp/secret.yml /secret.yml
EXPOSE 8080
CMD ["java", "-jar", "java-app.jar", "--param.value='The param Value'", "--spring.config.additional-location=file:/secret.yml,file:/config.yml"]