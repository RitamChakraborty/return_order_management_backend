FROM openjdk:11
ARG SERVICE_REGISTRY_JAR=service_registry/target/service_registry-1.0.jar
WORKDIR /app
COPY ${SERVICE_REGISTRY_JAR} service_registry.jar
ENTRYPOINT ["java","-jar","service_registry.jar"]