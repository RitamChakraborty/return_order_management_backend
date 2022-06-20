FROM ritamchakraborty/return_order_service_registry:1.0
ARG SERVICE_REGISTRY_JAR=app.jar
WORKDIR /app
COPY ${SERVICE_REGISTRY_JAR} ./service_registry.jar
ENTRYPOINT ["java","-jar","service_registry.jar"]