# Use maven runtime to build jar
FROM maven:3.9.9-eclipse-temurin-22-alpine AS build

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file into the container
COPY --from=build /app/target/patient-management-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 5050

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
