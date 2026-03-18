FROM maven:3.9.9-eclipse-temurin-21-alpine AS build

WORKDIR /build

# Instala dos2unix
RUN apk add --no-cache dos2unix

COPY . .

# Converte o mvnw para o formato unix
RUN dos2unix ./mvnw

RUN chmod +x ./mvnw
RUN ./mvnw dependency:go-offline -B
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-alpine AS deploy

WORKDIR /app
COPY --from=build /build/agendamento-service/target/agendamento-service-1.1-SNAPSHOT.jar /app/infomed.jar
ENTRYPOINT ["java","-jar","infomed.jar"]