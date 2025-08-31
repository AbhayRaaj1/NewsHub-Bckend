FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy everything
COPY . .

# Make mvnw executable
RUN chmod +x mvnw

# Build the jar
RUN ./mvnw clean package -DskipTests

# Run the jar
CMD ["java", "-jar", "target/backend-0.0.1-SNAPSHOT.jar"]