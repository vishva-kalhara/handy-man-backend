FROM openjdk:17-jdk-alpine

RUN addgroup -S app && adduser -S app -G app

WORKDIR /home/app

COPY target/handy-man-backend-0.0.1-SNAPSHOT.jar app.jar

USER app

ENTRYPOINT ["java", "-jar", "/app.jar"]