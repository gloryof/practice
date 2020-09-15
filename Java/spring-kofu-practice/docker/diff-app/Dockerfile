# syntax = docker/dockerfile:1.0-experimental
FROM maven:3.6.3-jdk-11-slim AS build-stage

WORKDIR /app

RUN apt-get update && apt-get install -y git
RUN git clone --no-checkout https://github.com/gloryof/practice.git \
    && cd practice \
    && git config core.sparsecheckout true \
    && touch .git/info/sparse-checkout \
    && echo 'Java/spring-kofu-practice/spring-kofu-diff-app' > .git/info/sparse-checkout \
    && git checkout

RUN --mount=type=cache,target=/root/.m2 \
    mvn clean package -f practice/Java/spring-kofu-practice/spring-kofu-diff-app/pom.xml

FROM openjdk:11-jre-slim AS exec-stage

RUN groupadd -r kofu-app && useradd -r -g kofu-app kofu-app

COPY --from=build-stage \
    /app/practice/Java/spring-kofu-practice/spring-kofu-diff-app/target/spring-kofu-diff-app-0.0.1-SNAPSHOT.jar \
    /app.jar

USER kofu-app

EXPOSE 8080

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/urandom","-jar", "app.jar"]