# 使用官方的 OpenJDK 镜像作为基础镜像
FROM openjdk:17-jdk-slim AS builder

WORKDIR /app

COPY gradlew /app/gradlew
COPY gradle /app/gradle
COPY build.gradle /app/build.gradle
COPY settings.gradle /app/settings.gradle
COPY src /app/src

RUN apt-get update && apt-get install -y dos2unix
RUN dos2unix /app/gradlew && chmod +x /app/gradlew

RUN ls -l /app/gradlew
RUN /app/gradlew build -x test

# 使用官方的 OpenJDK 镜像作为运行时基础镜像
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/build/libs/demo-0.0.1-SNAPSHOT.jar /app/demo.jar
ENTRYPOINT ["java", "-jar", "/app/demo.jar"]
