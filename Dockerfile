FROM openjdk:11 AS builder

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

RUN chmod +x ./gradlew
RUN ./gradlew bootJAR

FROM openjdk:11-jdk

# 위 builder에서 만든 .jar파일을 컨테이너 내부로 복사
COPY --from=builder build/libs/*.jar app.jar
#ARG JAR_FILE=./build/libs/*-SNAPSHOT.jar
#
#COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "/app.jar" ]