#!/bin/sh

docker-compose down
mvn clean package -DskipTests
docker-compose up -d --force-recreate --build