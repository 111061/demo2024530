FROM openjdk:17-jdk-slim

WORKDIR /app

COPY gradlew /app/gradlew
COPY gradle /app/gradle
COPY build.gradle /app/build.gradle
COPY settings.gradle /app/settings.gradle
COPY src /app/src

RUN apt-get update && apt-get install -y dos2unix
RUN dos2unix /app/gradlew && chmod +x /app/gradlew

RUN ls -l /app/gradlew
RUN ./gradlew build -x test

# 安装 curl
RUN apt-get update && apt-get install -y curl 

# 设置工作目录
WORKDIR /app/build/libs

# 运行构建的 JAR 文件
CMD ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar"]
