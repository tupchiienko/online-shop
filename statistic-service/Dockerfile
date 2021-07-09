FROM openjdk:16-alpine
COPY ./target/statistic-service-1.0-SNAPSHOT.jar statistic.jar
EXPOSE 8083
ENTRYPOINT ["sh", "-c", "java -jar statistic.jar"]