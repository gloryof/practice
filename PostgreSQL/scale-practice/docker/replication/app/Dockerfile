FROM maven:3.6.3-jdk-11 AS build-stage
WORKDIR /app
RUN apt-get install git
RUN git clone --no-checkout https://github.com/gloryof/practice.git \
    && cd practice \
    && git config core.sparsecheckout true \
    && touch .git/info/sparse-checkout \
    && echo "PostgreSQL/scale-practice/scale-app" > .git/info/sparse-checkout \
    && git checkout

RUN mvn clean package -DskipTests=true -f practice/PostgreSQL/scale-practice/scale-app

FROM openjdk:11-jre-slim AS exec-stage

COPY --from=build-stage \
    /app/practice/PostgreSQL/scale-practice/scale-app/target/scale-app-0.0.1-SNAPSHOT.jar \
    /config-app.jar

COPY ./config/application.yml /application.yml

EXPOSE 8080
CMD ["java", \
     "-jar", "config-app.jar", \
     "--spring.config.additional-location=file:/application.yml"]