FROM eclipse-temurin:17.0.11_9-jre-alpine

COPY target/tgbot-service-*.jar /java/tgbot-service.jar

WORKDIR /java

RUN ["pwd"]

ENTRYPOINT ["java", "-jar", "tgbot-service.jar"]