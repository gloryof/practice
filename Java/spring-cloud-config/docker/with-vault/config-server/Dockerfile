FROM maven:3.6.3-jdk-11 AS build-stage
WORKDIR /app
RUN apt-get install git
RUN git clone --no-checkout https://github.com/gloryof/practice.git \
    && cd practice \
    && git config core.sparsecheckout true \
    && touch .git/info/sparse-checkout \
    && echo "spring-cloud-config" > .git/info/sparse-checkout \
    && git checkout

RUN mvn clean package -DskipTests=true -f practice/Java/spring-cloud-config/config-server-with-vault/pom.xml

FROM openjdk:11-jre-slim AS exec-stage

ARG profile
ARG mq_user
ARG mq_pass

ENV MQ_USER=${mq_user}
ENV MQ_PASS=${mq_pass}

COPY --from=build-stage \
    /app/practice/Java/spring-cloud-config/config-server-with-vault/target/config-server-with-vault-0.0.1-SNAPSHOT.jar \
    /config-server.jar

RUN mkdir /app-config /server-config
COPY ./app-config/* /app-config/
COPY ./server-config/* /server-config/
COPY ./param/envfile /envfile


RUN ["/bin/bash", "-c", " \
    source /envfile && \
    sed -i -e s/__role-id__/${ROLE_ID}/ /server-config/server-config.yml && \
    sed -i -e s/__secret-id__/${SECRET_ID}/ /server-config/server-config.yml && \
    sed -i -e s/__mq-user__/${MQ_USER}/ /server-config/server-config.yml && \
    sed -i -e s/__mq-pass__/${MQ_PASS}/ /server-config/server-config.yml"]

EXPOSE 8080
CMD ["java", \
     "-jar", "config-server.jar", \
     "--spring.config.additional-location=file:/server-config/server-config.yml", \
     "--user.client.password=$2a$10$GhVXUbrMRq0B7KxebOjpYecDHt4UOMXD4ZSP8FokMMeOh2tk8Ltjm"]
