FROM openjdk:11
COPY ./shop-service/target/shop-service-1.0-SNAPSHOT.jar shop.jar
COPY ./wait-for-it.sh wait.sh
EXPOSE 8082
ENTRYPOINT ["sh", "-c", "./wait.sh config-service:8888 -- java -jar shop.jar"]