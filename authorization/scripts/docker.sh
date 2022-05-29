export CONFIG_SERVER_URL=http://config-server:8286
export SECRET_KEY=password
export DB_USERNAME=ritam
export DB_PASSWORD=password
docker tag authorization:1.0 ritamchakraborty/return_order_authorization:1.0
docker push ritamchakraborty/return_order_authorization:1.0
docker run -it --name authorization --network return-order-network -e CONFIG_SERVER_URL=http://config-server:8286 -e DB_USERNAME=ritam -e DB_PASSWORD=password -e SECRET_KEY=secret -p 8080:8080 ritamchakraborty/return_order_authorization:1.0
