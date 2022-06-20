FROM openjdk:11
WORKDIR /app
COPY service_registry/target/service_registry-1.0.jar service_registry.jar
ENTRYPOINT ["java","-jar","service_registry.jar"]