FROM openjdk:11
RUN cp -a service_registry/target/service_registry-1.0.jar service_registry.jar
ENTRYPOINT ["java","-jar","service_registry.jar"]