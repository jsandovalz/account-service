# Java image
FROM openjdk:17-jdk-slim

# app folder
WORKDIR /app

# Copy jar file generated
COPY target/*.jar account-service-0.0.1-SNAPSHOT.jar

# Expose the port
EXPOSE 8082

# command to exec app
ENTRYPOINT ["java", "-jar", "account-service-0.0.1-SNAPSHOT.jar"]