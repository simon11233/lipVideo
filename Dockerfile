FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY ./target/*.jar Inspur-Demo.jar
ENTRYPOINT ["java","-jar","/Inspur-Demo.jar", "&"]
