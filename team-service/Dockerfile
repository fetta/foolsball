FROM java:8-jre
MAINTAINER Jan Siekierski <pan.siekierski@gmail.com>

ADD ./target/team-service-1.0-SNAPSHOT.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/team-service.jar"]

EXPOSE 8090