FROM eclipse-temurin:21-jdk-alpine-3.20.1 AS build
WORKDIR /build
COPY . .
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine-3.20.1
WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
