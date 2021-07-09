FROM openjdk:16-alpine
COPY ./target/discovery-service-1.0-SNAPSHOT.jar discovery.jar
EXPOSE 8761
ENTRYPOINT ["sh", "-c", "java -jar discovery.jar"]