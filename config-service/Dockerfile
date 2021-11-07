FROM openjdk:11
COPY ./config-service/target/config-service-1.0-SNAPSHOT.jar config.jar
COPY ./wait-for-it.sh wait.sh
EXPOSE 8888
ENTRYPOINT ["sh", "-c", "./wait.sh postgres-db:5432 -- java -jar config.jar"]