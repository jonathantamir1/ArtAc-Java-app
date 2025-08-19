# Build stage is handled by Jenkins using Maven; here we just run the fat JAR
FROM openjdk:17-slim

ENV APP_HOME=/app \
    JAVA_OPTS="" \
    SERVER_PORT=8080

WORKDIR ${APP_HOME}

# Copy the Spring Boot jar (expecting Jenkins to have built it)
COPY target/chatbot-app-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

# Healthcheck (basic TCP check)
HEALTHCHECK --interval=30s --timeout=5s --start-period=20s --retries=3 \
  CMD wget -qO- http://localhost:${SERVER_PORT}/ || exit 1

ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar app.jar"]


