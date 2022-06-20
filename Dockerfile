FROM ritamchakraborty/return_order_service_registry:1.0
ENTRYPOINT ["java","-XX:MaxPermSize=128m","-jar","app.jar"]