# --- Stage 1: Build the Application ---
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy the project files
COPY pom.xml .
COPY src ./src

# Build the JAR file (skipping tests to speed up deployment)
RUN mvn clean package -DskipTests

# --- Stage 2: Run the Application ---
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy the built JAR from the previous stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port
EXPOSE 8080

# Command to run the app
ENTRYPOINT ["java", "-jar", "app.jar"]