#!/bin/bash

cd "${BASH_SOURCE%/*}" || exit
cd ..

echo 'Creating Docker Image ...'
mvn spring-boot:build-image
echo 'Running Docker Image ...'
docker run -it --name authorization --network return-order-network -e DB_USERNAME=ritam -e DB_PASSWORD=password -e SECRET_KEY=secret -p 8080:8080 ritamchakraborty/return_order_authorization:1.0
