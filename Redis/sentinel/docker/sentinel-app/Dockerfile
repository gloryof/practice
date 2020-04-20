# syntax = docker/dockerfile:experimental
FROM maven:3.6.3-jdk-11 AS build-stage
WORKDIR /app
RUN apt-get install git
RUN git clone --no-checkout https://github.com/gloryof/practice.git \
    && cd practice \
    && git config core.sparsecheckout true \
    && touch .git/info/sparse-checkout \
    && echo "Redis/sentinel" > .git/info/sparse-checkout \
    && git checkout

RUN mvn clean package -f practice/Redis/sentinel/redis-sentinel-app/pom.xml

FROM openjdk:11-jre-slim AS exec-stage
COPY --from=build-stage \
    /app/practice/Redis/sentinel/redis-sentinel-app/target/redis-sentinel-app-0.0.1-SNAPSHOT.jar \
    /redis-sentinel-app.jar
COPY /conf/conf.yml /config.yml

EXPOSE 8080
CMD ["java", "-jar", "redis-sentinel-app.jar", "--spring.config.additional-location=file:/config.yml"]