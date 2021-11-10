FROM openjdk:11-jdk-slim
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY sample.db sample.db
ENTRYPOINT ["java","-jar","/app.jar"]