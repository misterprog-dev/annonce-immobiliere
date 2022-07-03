FROM gradle:3.4-jdk7-alpine AS build
USER root
RUN mkdir -p /workspace
WORKDIR /workspace
COPY build.gradle settings.gradle /workspace/
COPY src /workspace/src
RUN gradle clean build --no-daemon


FROM openjdk:8-alpine
COPY --from=build /workspace/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]