FROM openjdk:20-jdk
ARG JAR_FILE=target/*.jar
COPY ./target/collage-automation-systems-backend-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]