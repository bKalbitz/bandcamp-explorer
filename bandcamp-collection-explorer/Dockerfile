FROM maven:3.9.11-amazoncorretto-24-alpine AS builder
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM amazoncorretto:24-alpine-jdk AS runner
COPY --from=builder /home/app/target/bandcamp-collection-explorer-0.0.1-SNAPSHOT.jar bandcamp-collection-explorer-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/bandcamp-collection-explorer-0.0.1-SNAPSHOT.jar","--spring.profiles.active=docker"]