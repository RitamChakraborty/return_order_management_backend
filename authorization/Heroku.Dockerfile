FROM openjdk:11
ARG JAR_FILE=target/*.jar
ARG DB_USERNAME=ritam
ARG DB_PASSWORD=password
ARG SECRET_KEY=secret
COPY $JAR_FILE app.jar