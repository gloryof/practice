# syntax = docker/dockerfile:1.0-experimental
FROM maven:3.6.3-jdk-11-slim AS build-stage

WORKDIR /app

RUN apt-get update && apt-get install -y git
RUN git clone --no-checkout https://github.com/gloryof/practice.git \
    && cd practice \
    && git config core.sparsecheckout true \
    && touch .git/info/sparse-checkout \
    && echo 'Auth/OAuth2/auth-practice-goods-app' > .git/info/sparse-checkout \
    && git checkout

RUN --mount=type=cache,target=/root/.m2 \
    mvn clean package -f practice/Auth/OAuth2/auth-practice-goods-app/pom.xml

FROM openjdk:11-jre-slim AS exec-stage

RUN groupadd -r tracing-app && useradd -r -g tracing-app tracing-app

COPY --from=build-stage \
    /app/practice/Auth/OAuth2/auth-practice-goods-app/target/auth-practice-goods-app-0.0.1-SNAPSHOT.jar \
    /app.jar

USER tracing-app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]