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
RUN /app/gradlew build -x test

# 安裝 curl
RUN apt-get update && apt-get install -y curl 

# 下载 JAR 文件
# 使用 GitHub Release 链接替换 <GITHUB_RELEASE_URL>
RUN curl -L -o demo-0.0.1-SNAPSHOT.jar "https://github.com/111061/demo2024530/releases/download/v1.0.0/demo-0.0.1-SNAPSHOT.jar"
# 设置环境变量
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://dpg-cpv6fblumphs73c6hgt0-a/test_5zth
ENV SPRING_DATASOURCE_USERNAME=test
ENV SPRING_DATASOURCE_PASSWORD=QQQqwfyUyF6jxNxskpeKLTsNThYLMNHf
ENV SPRING_DATASOURCE_DRIVER-CLASS-NAME=org.postgresql.Driver
ENV SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT=org.hibernate.dialect.PostgreSQLDialect


# 运行下载的 JAR 文件
CMD ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar"]


