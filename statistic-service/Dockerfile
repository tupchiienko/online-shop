FROM openjdk:11
COPY ./statistic-service/target/statistic-service-1.0-SNAPSHOT.jar statistic.jar
COPY ./wait-for-it.sh wait.sh
EXPOSE 8083
ENTRYPOINT ["sh", "-c", "./wait.sh config-service:8888 -- java -jar statistic.jar"]