docker tag config_server:1.0 ritamchakraborty/return_order_config_server:1.0
docker push ritamchakraborty/return_order_config_server:1.0
docker run -it --name config-server --network return-order-network -p 8286:8286 ritamchakraborty/return_order_config_server:1.0
