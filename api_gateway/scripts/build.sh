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
mvn spring-boot:build-image
docker tag api_gateway:1.0 ritamchakraborty/return_order_api_gateway:1.0
echo 'Pushing image to Docker Hub ...'
docker push ritamchakraborty/return_order_api_gateway:1.0
echo 'Running Docker Image ...'
docker run \
  -it \
  --name api-gateway \
  --network return-order-network \
  -e CONFIG_SERVER_URL=http://config-server:8286 \
  -p 8181:8181 \
  ritamchakraborty/return_order_api_gateway:1.0
