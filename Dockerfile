# Stage 1: Build
FROM eclipse-temurin:25-jdk-alpine AS build
RUN apk add --no-cache maven
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml ./
RUN mvn dependency:go-offline

# Copy source and build
COPY src src
RUN mvn package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:25-jre-alpine
WORKDIR /app

# Copy built JAR
COPY --from=build /app/target/*.jar app.jar

# Web port
EXPOSE 8086

ENTRYPOINT ["java", "-jar", "app.jar"]
