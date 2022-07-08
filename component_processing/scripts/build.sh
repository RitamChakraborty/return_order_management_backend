#!/bin/bash

cd "${BASH_SOURCE%/*}" || exit
cd ..

echo 'Creating JAR ...'
mvn clean install
echo 'Removing old Docker container ...'
docker container rm -f component-processing
echo 'Removing old Docker image ...'
docker rmi ritamchakraborty/return_order_component_processing:1.0
docker rmi component_processing:1.0
echo 'Creating docker image ...'
docker build -t component_processing:1.0 .
docker tag component_processing:1.0 ritamchakraborty/return_order_component_processing:1.0
echo 'Pushing image to Docker Hub ...'
docker push ritamchakraborty/return_order_component_processing:1.0
echo 'Running docker image ...'
docker run -it \
  --name component-processing \
  --network return-order-network \
  -e PROFILE=docker \
  -e DB_USERNAME=ritam \
  -e DB_PASSWORD=password \
  -p 8081:8081 \
  ritamchakraborty/return_order_component_processing:1.0
