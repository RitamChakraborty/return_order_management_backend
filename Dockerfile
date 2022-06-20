FROM ritamchakraborty/return_order_service_registry:1.0
RUN ls
RUN ls /app
ENTRYPOINT ["java","-jar","app.jar"]