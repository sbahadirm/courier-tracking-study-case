FROM openjdk:17-alpine
ARG JAR_FILE=target/courier-tracking-study-case-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]
EXPOSE 8080