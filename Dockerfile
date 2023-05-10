FROM openjdk:18.0.2.1-slim-buster
ARG JAR_FILE=target/sample.jar
WORKDIR /opt/app
ADD $JAR_FILE sample.jar
ENTRYPOINT ["java","-jar","sample.jar"]