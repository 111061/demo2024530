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
RUN apt-get update && apt-get install -y curl file


# 下载 JAR 文件并增加重试机制
# 将 <FILE_ID> 替换为您的 Google Drive 文件 ID
RUN curl -L --retry 5 --retry-delay 10 -o demo-0.0.1-SNAPSHOT.jar "https://drive.google.com/uc?export=download&id=13aS0PVsv3lVmhJDopeZqA9kp-bUiSFGf" \
    && echo "Downloaded JAR file size:" \
    && ls -lh demo-0.0.1-SNAPSHOT.jar \
    && echo "Checking file type:" \
    && file demo-0.0.1-SNAPSHOT.jar



# 运行下载的 JAR 文件
CMD ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar"]


