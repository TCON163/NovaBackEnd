FROM java:8

EXPOSE 8010

ADD target/discovery-service-0.0.1-SNAPSHOT.jar discovery-service.jar

ENTRYPOINT ["java", "-jar", "discovery-service.jar"]
