FROM openjdk:11
WORKDIR /app
COPY /media/ritam/Storage/Projects/IdeaProjects/Projects/return_order_management/service_registry ./service_regisry
ENTRYPOINT ["java","-jar","target/service_registry.jar"]