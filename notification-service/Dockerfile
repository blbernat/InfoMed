FROM eclipse-temurin:21-jre-focal
VOLUME /tmp
COPY target/notification-service-1.0-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app.jar"]