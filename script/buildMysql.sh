#!/bin/bash

YOUR_PASSWORD=""

docker pull mysql &&\
    docker run --name mysql4spring -e MYSQL_ROOT_PASSWORD="${YOUR_PASSWORD}" -p 3306:3306 -d mysql

echo "PLEASE create a database and create user with granted all privillage on it."
echo "THEN go to configure src/main/resources/application.properties"
