FROM eclipse-temurin:17-jdk-focal
RUN apt-get update && apt-get install -y curl
VOLUME /tmp
COPY target/payment-service-0.0.1-SNAPSHOT.jar app.jar
COPY src/main/resources/application.yml ./
ENTRYPOINT ["java", "-Dspring.autoconfigure.exclude=org.springframework.boot.actuate.autoconfigure.metrics.SystemMetricsAutoConfiguration,org.springframework.boot.actuate.autoconfigure.tracing.otel.OtelAutoConfiguration,org.springframework.boot.actuate.autoconfigure.tracing.zipkin.ZipkinAutoConfiguration", "-jar", "/app.jar"]
