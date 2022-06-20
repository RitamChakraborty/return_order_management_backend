FROM ritamchakraborty/return_order_service_registry:1.0
ENTRYPOINT ["java","-Xmx512M","-jar","app.jar"]