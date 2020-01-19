FROM maven:3.6.3-jdk-11 AS build-stage
WORKDIR /app
RUN apt-get install git
RUN git clone --no-checkout https://github.com/gloryof/practice.git \
    && cd practice \
    && git config core.sparsecheckout true \
    && touch .git/info/sparse-checkout \
    && echo "container" > .git/info/sparse-checkout \
    && git checkout

RUN mvn clean package -f practice/container/container-java/java-app/pom.xml

FROM openjdk:11-jre-slim AS exec-stage
COPY --from=build-stage \
    /app/practice/container/container-java/java-app/target/java-app-0.0.1-SNAPSHOT.jar \
    /java-app.jar
EXPOSE 8080
CMD ["java", "-jar", "java-app.jar"]