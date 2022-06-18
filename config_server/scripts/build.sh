#!/bin/bash

cd "${BASH_SOURCE%/*}" || exit
cd ..

echo 'Creating JAR ...'
mvn clean install
echo 'Removing old container ...'
docker container rm -f config-server
echo 'Removing old Docker image ...'
docker rmi ritamchakraborty/return_order_config_server:1.0
docker rmi config_server:1.0
echo 'Creating Docker Image ...'
mvn spring-boot:build-image
docker tag config_server:1.0 ritamchakraborty/return_order_config_server:1.0
echo 'Pushing image to Docker Hub ...'
docker push ritamchakraborty/return_order_config_server:1.0
echo 'Running Docker Image ...'
docker run \
  -it \
  --name config-server \
  --network return-order-network \
  -e SERVICE_REGISTRY_URL=http://service-registry:8761/eureka \
  -p 8286:8286 \
  ritamchakraborty/return_order_config_server:1.0
