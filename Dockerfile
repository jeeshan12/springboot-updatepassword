FROM openjdk:11.0.4-jre-slim-buster
LABEL author="Mohd Jeeshan"
WORKDIR /app
COPY target/changepasswordservice.jar changepasswordservice.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "changepasswordservice.jar","-web", "-webAllowOthers", "-tcp", "-tcpAllowOthers", "-browser"]
