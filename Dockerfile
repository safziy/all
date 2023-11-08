FROM openjdk:17

COPY ./build/libs/all-0.0.1-SNAPSHOT.jar /app.jar

ENTRYPOINT ["java","-jar","app.jar"]