FROM openjdk:17-jdk-alpine

RUN addgroup -S app && adduser -S app -G app

USER app

COPY target/handy-man-backend-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]