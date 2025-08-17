FROM openjdk:17-jdk-alpine
RUN apk add --no-cache curl
VOLUME /tmp
COPY target/payment-service-0.0.1-SNAPSHOT.jar app.jar
COPY src/main/resources/application.yml ./
ENTRYPOINT ["java", "-jar", "/app.jar"]
