# syntax = docker/dockerfile:1.0-experimental
FROM maven:3.6.3-jdk-11-slim AS build-stage

WORKDIR /app

RUN apt-get update && apt-get install -y git
RUN git clone --no-checkout https://github.com/gloryof/practice.git \
    && cd practice \
    && git config core.sparsecheckout true \
    && touch .git/info/sparse-checkout \
    && echo 'Java/monitor-jvm-practice/monitor-jvm-practice-app/' > .git/info/sparse-checkout \
    && git checkout

RUN --mount=type=cache,target=/root/.m2 \
    mvn clean package -f practice/Java/monitor-jvm-practice/monitor-jvm-practice-app/pom.xml

FROM openjdk:11-jre-slim AS exec-stage

RUN groupadd -r metrics-app && useradd -r -g metrics-app metrics-app

COPY --from=build-stage \
    /app/practice/Java/monitor-jvm-practice/monitor-jvm-practice-app/target/monitor-jvm-practice-app-0.0.1-SNAPSHOT.jar \
    /app.jar

USER metrics-app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]