# syntax = docker/dockerfile:1.0-experimental
FROM maven:3.6.3-jdk-11-slim AS build-stage

WORKDIR /app

RUN apt-get update && apt-get install -y git
RUN git clone --no-checkout https://github.com/gloryof/practice.git \
    && cd practice \
    && git config core.sparsecheckout true \
    && touch .git/info/sparse-checkout \
    && echo 'Kubernetes/practice-k8s/k8s-redis-app' > .git/info/sparse-checkout \
    && git checkout

RUN --mount=type=cache,target=/root/.m2 \
    mvn clean package -f practice/Kubernetes/practice-k8s/k8s-redis-app/pom.xml

FROM openjdk:11-jre-slim AS exec-stage

RUN groupadd -r redis-app && useradd -r -g redis-app redis-app

COPY --from=build-stage \
    /app/practice/Kubernetes/practice-k8s/k8s-redis-app/target/k8s-redis-app-0.0.1-SNAPSHOT.jar \
    /k8s-redis-app.jar

USER redis-app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "k8s-redis-app.jar"]