FROM maven:3.9.9-eclipse-temurin-21-alpine AS build

WORKDIR /build

COPY . .

RUN ./mvnw dependency:go-offline -B
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-alpine AS deploy

WORKDIR /app
COPY --from=build /build/notification-service/target/notification-service-1.0-SNAPSHOT.jar /app/infomed.jar
ENTRYPOINT ["java","-jar","infomed.jar"]