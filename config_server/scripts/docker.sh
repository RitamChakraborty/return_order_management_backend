export SERVICE_REGISTRY_URL=http://service-registry:8761/eureka
docker tag config_server:1.0 ritamchakraborty/return_order_config_server:1.0
docker push ritamchakraborty/return_order_config_server:1.0
docker run -it --name config-server --network return-order-network -e SERVICE_REGISTRY_URL=http://service-registry:8761/eureka -p 8286:8286 ritamchakraborty/return_order_config_server:1.0
