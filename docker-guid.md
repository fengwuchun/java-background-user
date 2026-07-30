# Docker 部署说明

## 1. 项目部署目标

本项目已配置为基于 Docker Compose 的多容器部署方案，包含：
- 后端服务：Spring Boot 应用
- 数据库服务：MySQL 8.4

## 2. 关键部署文件

### 2.1 Dockerfile
文件：Dockerfile

作用：构建后端应用镜像。

内容摘要：
```dockerfile
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY target/backend-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
```

### 2.2 docker-compose.yml
文件：docker-compose.yml

作用：定义 MySQL 和后端两个服务，统一启动。

当前服务配置：
- mysql：端口 3306
- backend：端口 8080

### 2.3 application-docker.yaml
文件：src/main/resources/application-docker.yaml

作用：为容器环境下的 Spring Boot 应用提供数据库连接配置。

关键配置：
- 数据库地址：mysql:3306
- 数据库名：city_db_restore
- 用户名：backend_user
- 密码：123456

## 3. 部署前准备

### 3.1 确保项目已打包
在项目根目录执行：
```bash
./mvnw -DskipTests package
```

### 3.2 确保 Docker 已安装并可用
```bash
docker --version
docker compose version
```

## 4. 常用部署命令

### 4.1 构建镜像
```bash
docker build -t backend:latest .
```

### 4.2 启动所有容器
```bash
docker compose up -d
```

### 4.3 查看容器状态
```bash
docker compose ps
```

### 4.4 查看日志
```bash
docker compose logs -f
```

### 4.5 停止容器
```bash
docker compose down
```

## 5. 服务访问地址

### 后端接口
```text
http://localhost:8080
```

### MySQL
```text
localhost:3306
```

## 6. 已验证接口

当前已经验证可访问：
- http://localhost:8080/city
- http://localhost:8080/user

## 7. 说明

如果后续修改了项目代码，建议重新执行：
```bash
./mvnw -DskipTests package
docker build -t backend:latest .
docker compose up -d --build
```


```bash 流程

修改代码
    ↓
mvn clean package
    ↓
docker build -t backend .
    ↓
docker compose down  （docker compose down -v 删除数据卷）
    ↓
docker compose up
    ↓
访问 localhost:8080

```
