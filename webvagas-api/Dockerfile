FROM eclipse-temurin:21-jdk-alpine-3.22 AS build
WORKDIR /build
COPY . .

RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine-3.22
WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
