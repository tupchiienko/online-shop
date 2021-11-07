FROM openjdk:11
COPY ./auth-service/target/auth-service-1.0-SNAPSHOT.jar auth.jar
COPY ./wait-for-it.sh wait.sh
EXPOSE 8081
ENTRYPOINT ["sh", "-c", "./wait.sh config-service:8888 -- java -jar auth.jar"]
