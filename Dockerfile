# 使用官方的 OpenJDK 17 作为基础镜像
FROM openjdk:17-jdk-slim

# 设置工作目录
WORKDIR /app

# 将项目的所有文件复制到容器中
COPY . .

# 构建项目
RUN ./gradlew build

# 暴露应用程序运行的端口（假设是8080）
EXPOSE 8080

# 运行生成的 JAR 文件
CMD ["java", "-jar", "build/libs/demo-0.0.1-SNAPSHOT.jar"]
