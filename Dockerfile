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

# 下載 JAR 文件
# 將 <GOOGLE_DRIVE_FILE_ID> 替換為您的Google Drive文件ID
RUN curl -L -o demo-0.0.1-SNAPSHOT.jar "https://drive.google.com/uc?export=download&id=13aS0PVsv3lVmhJDopeZqA9kp-bUiSFGf"

# 设置环境变量
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://dpg-cpv6fblumphs73c6hgt0-a/test_5zth
ENV SPRING_DATASOURCE_USERNAME=test
ENV SPRING_DATASOURCE_PASSWORD=QQQqwfyUyF6jxNxskpeKLTsNThYLMNHf
ENV SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT=org.hibernate.dialect.PostgreSQLDialect


# 运行下载的 JAR 文件
CMD ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar"]


