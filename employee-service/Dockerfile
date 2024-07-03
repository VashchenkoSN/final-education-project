FROM eclipse-temurin:17.0.11_9-jre-alpine

COPY target/employee-service*.jar /java/employee-service.jar

WORKDIR /java

RUN ["pwd"]

ENTRYPOINT ["java", "-jar", "employee-service.jar"]