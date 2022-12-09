FROM adoptopenjdk/openjdk11
VOLUME /tmp
ARG JAR_FILE=build/libs/hc-cloud-gateway.jar
ADD ${JAR_FILE} hc-cloud-gateway.jar
EXPOSE 5000
ENTRYPOINT ["java","-Dspring.profiles.active=dev","-jar","hc-cloud-gateway.jar"]
