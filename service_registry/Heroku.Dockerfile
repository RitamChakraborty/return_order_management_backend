FROM ritamchakraborty/return_order_service_registry:1.0
ENTRYPOINT ["java","-Xmx512M","-Dserver.port=$PORT","-jar","app.jar"]