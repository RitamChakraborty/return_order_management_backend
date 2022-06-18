#!/bin/bash

cd "${BASH_SOURCE%/*}" || exit
cd ..

echo 'Creating JAR ...'
mvn clean install
echo 'Removing old Docker container ...'
docker container rm -f authorization
echo 'Removing old Docker image ...'
docker rmi ritamchakraborty/return_order_authorization:1.0
docker rmi authorization:1.0
echo 'Creating Docker Image ...'
docker build -t authorization:1.0 .
docker tag authorization:1.0 ritamchakraborty/return_order_authorization:1.0
echo 'Pushing image to Docker Hub ...'
docker push ritamchakraborty/return_order_authorization:1.0
echo 'Running Docker Image ...'
docker run \
  -it \
  --name authorization \
  --network return-order-network \
  -e CONFIG_SERVER_URL=http://config-server:8286 \
  -e DB_USERNAME=ritam \
  -e DB_PASSWORD=password \
  -e SECRET_KEY=secret \
  -p 8080:8080 \
  ritamchakraborty/return_order_authorization:1.0
