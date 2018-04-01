FROM openjdk:9

ARG appPort=8080

COPY build/monitoring-app-0.0.1-SNAPSHOT.jar monitoring-app.jar
COPY conf/monitoring-app.yml monitoring-app.yml

CMD ["java", "-jar", "monitoring-app.jar", "--spring.config.additional-location=monitoring-app.yml","--server.port=${APP_PORT}"]
