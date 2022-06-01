#!/bin/bash

cd "${BASH_SOURCE%/*}" || exit
cd ..

echo 'Creating JAR ...'
mvn clean install
echo 'Removing old container ...'
docker container rm -f service-registry
echo 'Removing old Docker image ...'
docker rmi ritamchakraborty/return_order_service_registry:1.0
docker rmi service_registry:1.0
echo 'Creating Docker Image ...'
mvn spring-boot:build-image
docker tag service_registry:1.0 ritamchakraborty/return_order_service_registry:1.0
echo 'Pushing image to Docker Hub ...'
docker push ritamchakraborty/return_order_service_registry:1.0
echo 'Running Docker Image ...'
docker run -it --name service-registry --network return-order-network -p 8761:8761 ritamchakraborty/return_order_service_registry:1.0
