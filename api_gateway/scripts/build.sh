#!/bin/bash

cd "${BASH_SOURCE%/*}" || exit
cd ..

echo 'Creating JAR ...'
mvn clean install
echo 'Removing old Docker container ...'
docker container rm -f api-gateway
echo 'Removing old Docker image ...'
docker rmi ritamchakraborty/return_order_api_gateway:1.0
docker rmi api_gateway:1.0
echo 'Creating docker image ...'
docker build -t api_gateway:1.0 .
docker tag api_gateway:1.0 ritamchakraborty/return_order_api_gateway:1.0
echo 'Pushing image to Docker Hub ...'
docker push ritamchakraborty/return_order_api_gateway:1.0
echo 'Running Docker Image ...'
docker run \
  -it \
  --name api-gateway \
  --network return-order-network \
  -e PROFILE=docker \
  -p 8181:8181 \
  ritamchakraborty/return_order_api_gateway:1.0
