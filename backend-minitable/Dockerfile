#
# Build stage
#
FROM maven:3.8.6-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN --mount=type=cache,target=/root/.m2 mvn -f /home/app/pom.xml clean package -Dmaven.test.skip

#
# Package stage
#
FROM openjdk:11-jre-slim as backendApp
ENTRYPOINT ["sudo", "mkdir", "/app/"]
WORKDIR app
COPY --from=build /home/app/target/MiniGoogleTable-0.1.0-SNAPSHOT.jar /app
RUN apt-get update; apt-get install -y fontconfig libfreetype6
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app/MiniGoogleTable-0.1.0-SNAPSHOT.jar"]