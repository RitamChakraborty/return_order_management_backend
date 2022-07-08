FROM openjdk:11
ARG DB_USERNAME=ritam
ARG DB_PASSWORD=password
ARG PACKAGING_AND_DELIVERY_API_URL=https://packaging-and-delivery-ritam.herokuapp.com
ARG JAR_FILE=target/*.jar
COPY $JAR_FILE app.jar