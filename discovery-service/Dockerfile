FROM openjdk:11
COPY ./discovery-service/target/discovery-service-1.0-SNAPSHOT.jar discovery.jar
COPY ./wait-for-it.sh wait.sh
EXPOSE 8761
ENTRYPOINT ["sh", "-c", "./wait.sh config-service:8888 -- java -jar discovery.jar"]