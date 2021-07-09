FROM openjdk:16-alpine
COPY ./target/config-service-1.0-SNAPSHOT.jar config.jar
EXPOSE 8888
ENTRYPOINT ["sh", "-c", "java -jar config.jar"]