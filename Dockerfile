# Use a lightweight JDK image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml first (for caching)
COPY mvnw pom.xml ./
COPY .mvn .mvn

# Copy source code
COPY src/ ./src

# Build the app
RUN ./mvnw clean package -DskipTests -Dcheckstyle.skip=true

# Expose port 8080
EXPOSE 8080

# Run the app
CMD ["java", "-jar", "target/car360-0.0.1-SNAPSHOT.jar"]
