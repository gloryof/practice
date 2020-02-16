FROM maven:3.6.3-jdk-11 AS build-stage
WORKDIR /app
RUN apt-get install git
RUN git clone --no-checkout https://github.com/gloryof/practice.git \
    && cd practice \
    && git config core.sparsecheckout true \
    && touch .git/info/sparse-checkout \
    && echo "spring-cloud-config" > .git/info/sparse-checkout \
    && git checkout

RUN mvn clean package -DskipTests=true -f practice/Java/spring-cloud-config/config-server/pom.xml

FROM openjdk:11-jre-slim AS exec-stage

ARG profile

COPY --from=build-stage \
    /app/practice/Java/spring-cloud-config/config-server/target/config-server-0.0.1-SNAPSHOT.jar \
    /config-server.jar

RUN mkdir /app-config /server-config
COPY ./app-config/* /app-config/
COPY ./server-config/* /server-config/

EXPOSE 8080
CMD ["java", \
     "-jar", "config-server.jar", \
     "--spring.config.additional-location=file:/server-config/server-config.yml"]
