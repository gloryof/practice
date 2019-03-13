FROM openjdk:11

COPY build/monitoring-app.jar monitoring-app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/monitoring-app.jar","--spring.datasource.url=jdbc:postgresql://mon-pro-db:5432/monitor-db"]
