FROM openjdk:11
COPY ./gateway-service/target/gateway-service-1.0-SNAPSHOT.jar gateway.jar
COPY ./wait-for-it.sh wait.sh
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "./wait.sh config-service:8888 -- java -jar gateway.jar"]