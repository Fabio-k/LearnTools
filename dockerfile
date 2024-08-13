FROM openjdk:17-slim

VOLUME /tmp

ARG JAR_FILE=backend/target/LearnToolsApi-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT [ "java", "-jar", "/app.jar" ]