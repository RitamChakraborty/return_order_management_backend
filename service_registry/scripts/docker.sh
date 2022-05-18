docker tag service_registry:1.0 ritamchakraborty/return_order_service_registry:1.0
docker push ritamchakraborty/return_order_service_registry:1.0
docker run -it --name service-registry --network return-order-network -p 8761:8761 ritamchakraborty/return_order_service_registry:1.0
