FROM maven:3.9.11-amazoncorretto-24-alpine AS builder
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM amazoncorretto:24-alpine-jdk
COPY --from=builder /home/app/target/bandcamp-collection-intersection-0.0.1-SNAPSHOT.jar bandcamp-collection-intersection-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/bandcamp-collection-intersection-0.0.1-SNAPSHOT.jar","--collection.service.host=http://bandcamp-collection-explorer:8080"]