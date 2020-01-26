FROM openjdk:11-jre-slim AS exec-stage
COPY  artifact/java-app.jar /java-app.jar
EXPOSE 8080
CMD ["java", "-jar", "java-app.jar"]