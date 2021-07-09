#!/bin/sh

mvn package -DskipTests

docker-compose up -d --force-recreate --build