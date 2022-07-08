#!/bin/bash

cd "${BASH_SOURCE%/*}" || exit
cd ..

echo 'Creating JAR ...'
mvn clean install
echo 'Removing old Docker container ...'
docker container rm -f packaging-and-delivery
echo 'Removing old Docker image ...'
docker rmi ritamchakraborty/return_order_packaging_and_delivery:1.0
docker rmi packaging_and_delivery:1.0
echo 'Creating docker image ...'
docker build -t packaging_and_delivery:1.0 .
docker tag packaging_and_delivery:1.0 ritamchakraborty/return_order_packaging_and_delivery:1.0
echo 'Pushing image to Docker Hub ...'
docker push ritamchakraborty/return_order_packaging_and_delivery:1.0
echo 'Running docker image ...'
docker run \
  -it \
  --name packaging-and-delivery \
  --network return-order-network \
  -e PROFILE=docker \
  -p 8082:8082 \
  ritamchakraborty/return_order_packaging_and_delivery:1.0
