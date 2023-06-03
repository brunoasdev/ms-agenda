FROM openjdk:17-jdk-slim
ARG JAR_FILE=build/libs/ms-agenda-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]