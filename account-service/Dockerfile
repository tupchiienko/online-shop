FROM openjdk:16-alpine
COPY ./target/account-service-1.0-SNAPSHOT.jar account.jar
EXPOSE 8081
ENTRYPOINT ["sh", "-c", "java -jar account.jar"]
