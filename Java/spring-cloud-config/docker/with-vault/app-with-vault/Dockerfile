FROM maven:3.6.3-jdk-11 AS build-stage
WORKDIR /app
RUN apt-get install git
RUN git clone --no-checkout https://github.com/gloryof/practice.git \
    && cd practice \
    && git config core.sparsecheckout true \
    && touch .git/info/sparse-checkout \
    && echo "spring-cloud-config" > .git/info/sparse-checkout \
    && git checkout

RUN mvn clean package -DskipTests=true -f practice/Java/spring-cloud-config/config-app-with-vault/pom.xml

FROM openjdk:11-jre-slim AS exec-stage

ARG profile
ARG mq_user
ARG mq_pass

ENV ACTIVE_PROFILE=${profile}
ENV MQ_USER=${mq_user}
ENV MQ_PASS=${mq_pass}

COPY --from=build-stage \
    /app/practice/Java/spring-cloud-config/config-app-with-vault/target/config-app-with-vault-0.0.1-SNAPSHOT.jar \
    /config-app.jar

COPY ./config/bootstrap.yml /bootstrap.yml
COPY ./param/envfile /envfile

RUN ["/bin/bash", "-c", " \
    source /envfile && \
    sed -i -e s/__role__/${ACTIVE_PROFILE}-db-user/ /bootstrap.yml && \
    sed -i -e s/__role-id__/${ROLE_ID}/ /bootstrap.yml && \
    sed -i -e s/__secret-id__/${SECRET_ID}/ /bootstrap.yml && \
    sed -i -e s/__mq-user__/${MQ_USER}/ /bootstrap.yml && \
    sed -i -e s/__mq-pass__/${MQ_PASS}/ /bootstrap.yml"]

EXPOSE 8080
CMD ["java", \
     "-jar", "config-app.jar", \
     "--spring.profiles.active=${ACTIVE_PROFILE}", \
     "--spring.config.additional-location=file:/bootstrap.yml"]
