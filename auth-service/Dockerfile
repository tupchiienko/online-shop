FROM openjdk:16-alpine
COPY ./target/auth-service-1.0-SNAPSHOT.jar auth.jar
EXPOSE 8081
ENTRYPOINT ["sh", "-c", "java -jar auth.jar"]
