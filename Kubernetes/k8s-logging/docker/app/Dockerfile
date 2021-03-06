# syntax = docker/dockerfile:1.0-experimental
FROM maven:3.6.3-jdk-11-slim AS build-stage

WORKDIR /app

RUN apt-get update && apt-get install -y git
RUN git clone --no-checkout https://github.com/gloryof/practice.git \
    && cd practice \
    && git config core.sparsecheckout true \
    && touch .git/info/sparse-checkout \
    && echo 'Kubernetes/k8s-logging/k8s-logging-app' > .git/info/sparse-checkout \
    && git checkout

RUN --mount=type=cache,target=/root/.m2 \
    mvn clean package -f practice/Kubernetes/k8s-logging/k8s-logging-app/pom.xml

FROM openjdk:11-jre-slim AS exec-stage

RUN groupadd -r logging-app && useradd -r -g logging-app logging-app

COPY --from=build-stage \
    /app/practice/Kubernetes/k8s-logging/k8s-logging-app/target/k8s-logging-app-0.0.1-SNAPSHOT.jar \
    /k8s-logging-app.jar

USER logging-app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "k8s-logging-app.jar"]