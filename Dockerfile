# 使用OpenJDK基础镜像
FROM openjdk:11-jre-slim

# 设置工作目录
WORKDIR /app

# 安装 curl
RUN apt-get update && apt-get install -y curl

# 下载第一个 JAR 文件
# 将 <GOOGLE_DRIVE_FILE_ID_1> 替换为您的第一个Google Drive文件ID
RUN curl -L -o demo-0.0.1-SNAPSHOT-plain.jar "https://drive.google.com/uc?export=download&id=13R8fcDb_DWh1PoauTrsetbbyHd6UybKv"

# 下载第二个 JAR 文件
# 将 <GOOGLE_DRIVE_FILE_ID_2> 替换为您的第二个Google Drive文件ID
RUN curl -L -o demo-0.0.1-SNAPSHOT.jar "https://drive.google.com/uc?export=download&id=1suWMU8hu6sNlK0xnpAYR536qcxKM3LM4"

# 如果您想要同时运行两个JAR文件，可以使用以下CMD
CMD ["sh", "-c", "java -jar demo-0.0.1-SNAPSHOT-plain.jar & java -jar demo-0.0.1-SNAPSHOT.jar"]