FROM adoptopenjdk/openjdk11
ARG JAR_FILE=target/hc-cloud-gateway.jar
ADD ${JAR_FILE} hc-cloud-gateway.jar
EXPOSE 5000
ENTRYPOINT ["java","-Dspring.profiles.active=dev","-jar","hc-cloud-gateway.jar"]
