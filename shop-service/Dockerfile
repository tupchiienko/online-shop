FROM openjdk:16-alpine
COPY ./target/shop-service-1.0-SNAPSHOT.jar shop.jar
EXPOSE 8082
ENTRYPOINT ["sh", "-c", "java -jar shop.jar"]