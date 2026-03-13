FROM eclipse-temurin:21-jre-focal
VOLUME /tmp
COPY target/agendamento-service-1.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]