FROM adoptopenjdk/openjdk11:x86_64-alpine-jre-11.0.18_10
ARG JAR_FILE=impl/target/microservice-post-impl-1.0.0-SNAPSHOT.jar
WORKDIR /app
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar","app.jar"]
EXPOSE 8080