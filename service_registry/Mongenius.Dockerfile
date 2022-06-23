FROM ritamchakraborty/return_order_service_registry:1.0
EXPOSE 8761
ENTRYPOINT ["java","-jar","app.jar"]