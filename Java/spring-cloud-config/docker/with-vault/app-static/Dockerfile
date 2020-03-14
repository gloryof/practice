FROM maven:3.6.3-jdk-11 AS build-stage
WORKDIR /app
RUN apt-get install git
RUN git clone --no-checkout https://github.com/gloryof/practice.git \
    && cd practice \
    && git config core.sparsecheckout true \
    && touch .git/info/sparse-checkout \
    && echo "spring-cloud-config" > .git/info/sparse-checkout \
    && git checkout

RUN mvn clean package -DskipTests=true -f practice/Java/spring-cloud-config/config-app/pom.xml

FROM openjdk:11-jre-slim AS exec-stage

ARG profile
ENV ACTIVE_PROFILE=${profile}

COPY --from=build-stage \
    /app/practice/Java/spring-cloud-config/config-app/target/config-app-0.0.1-SNAPSHOT.jar \
    /config-app.jar

COPY ./config/bootstrap.yml /bootstrap.yml

EXPOSE 8080
CMD ["java", \
     "-jar", "config-app.jar", \
     "--spring.profiles.active=${ACTIVE_PROFILE}", \
     "--spring.config.additional-location=file:/bootstrap.yml"]
