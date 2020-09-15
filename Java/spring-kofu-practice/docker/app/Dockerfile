# syntax = docker/dockerfile:1.0-experimental
FROM gradle:6.6.1-jdk11 AS build-stage

WORKDIR /app

RUN apt-get update && apt-get install -y git
RUN git clone --no-checkout https://github.com/gloryof/practice.git \
    && cd practice \
    && git config core.sparsecheckout true \
    && touch .git/info/sparse-checkout \
    && echo 'Java/spring-kofu-practice/spring-kofu-app' > .git/info/sparse-checkout \
    && git checkout

WORKDIR practice/Java/spring-kofu-practice/spring-kofu-app
RUN gradle clean build

FROM openjdk:11-jre-slim AS exec-stage

RUN groupadd -r kofu-app && useradd -r -g kofu-app kofu-app

COPY --from=build-stage \
    /app/practice/Java/spring-kofu-practice/spring-kofu-app/build/libs/spring-kofu-app-0.0.1-SNAPSHOT.jar \
    /app.jar

USER kofu-app

EXPOSE 8080

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/urandom","-jar", "app.jar"]