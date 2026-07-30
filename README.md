# MCP Server 详细文档

## 📋 概述

MCP (Model Context Protocol) Server 是本项目提供的MySQL专用数据访问服务，旨在为前端或其他客户端提供直接访问MySQL数据库的能力。

## 🚀 功能特性

- **直接数据库访问**: 提供对MySQL数据库的直接操作接口
- **资源管理**: 通过资源提供者统一管理数据库连接
- **协议支持**: 支持Model Context Protocol for MySQL
- **安全控制**: 内置访问权限验证机制

## 🔧 配置说明

### application.yaml 配置
```yaml
mcp:
  server:
    enabled: true
    endpoint: /mcp
  datasource:
    max-connections: 20
    connection-timeout: 30000
```

## 📡 API 接口

### 1. 数据库连接测试
- **接口**: `GET /mcp/health`
- **描述**: 测试数据库连接状态
- **响应**: 
```json
{
  "status": "UP",
  "database": "city_db",
  "timestamp": "2025-05-13T10:00:00"
}
```

### 2. 执行SQL查询
- **接口**: `POST /mcp/query`
- **描述**: 执行自定义SQL查询
- **请求体**:
```json
{
  "sql": "SELECT * FROM city WHERE id = ?",
  "parameters": [1]
}
```

## ⚙️ 架构设计

```
┌─────────────────────┐
│   McpController      │  ← MCP API 接口
├─────────────────────┤
│   McpServerConfig    │  ← MCP 服务配置
├─────────────────────┤
│ MySqlResourceProvider│  ← 数据库资源管理
└─────────────────────┘
```

## 🔄 使用流程

1. 确保MySQL服务正常运行
2. 启动Spring Boot应用
3. 访问 `/mcp/health` 验证服务状态
4. 通过MCP API执行数据库操作

## 📝 注意事项

- 生产环境建议启用身份验证
- 避免执行危险的SQL语句
- 定期监控连接池状态
- 敏感操作应记录审计日志

**最后更新时间**: 2025-05-13
package com.demo.backend.config;

import org.springframework.stereotype.Component;

/**
 * MySQL 资源提供者
 * 管理MySQL数据库连接和资源
 */
@Component
public class MySqlResourceProvider {
    
}
package com.demo.backend.config;

import org.springframework.context.annotation.Configuration;

/**
 * MCP Server 配置类
 * 配置MCP Server相关参数和组件
 */
@Configuration
public class McpServerConfig {
    
}
package com.demo.backend.controller;

import org.springframework.web.bind.annotation.RestController;

/**
 * MCP Server - MySQL 数据访问 API
 * 提供直接访问MySQL数据库的API接口
 */
@RestController
public class McpController {
    
}
# Backend 项目说明文档

## 📋 项目概述

### 1.1 项目简介
本项目是一个基于 **Spring Boot 4.1.0** 的城市信息管理后端服务，提供城市数据的 CRUD（增删改查）RESTful API 接口。

### 1.2 技术栈
- **后端框架**: Spring Boot 4.1.0
- **Java 版本**: JDK 17
- **构建工具**: Maven
- **数据库**: MySQL
- **ORM 框架**: Spring Data JPA (Jakarta Persistence)
- **简化代码**: Lombok 1.18.24
- **Web 框架**: Spring Web MVC
- **热部署**: Spring DevTools
- **MCP Server**: Model Context Protocol for MySQL

### 1.3 项目结构
```
backend/
├── src/main/java/com/demo/backend/
│   ├── BackendApplication.java          # 应用启动类
│   ├── controller/
│   │   └── CityController.java          # 控制器层 - REST API 接口
│   ├── service/
│   │   └── CityService.java             # 服务层 - 业务逻辑
│   ├── respository/
│   │   └── CityRepository.java          # 数据访问层 - JPA Repository
│   └── entity/
│       └── City.java                    # 实体类 - 数据模型
├── src/main/resources/
│   └── application.yaml                 # 应用配置文件
├── pom.xml                              # Maven 依赖配置
└── README.md                            # 项目说明文档
```

---

## 🚀 快速开始

### 2.1 环境要求
- **JDK**: 17 或更高版本
- **Maven**: 3.6+ 
- **MySQL**: 5.7+ 或 8.0+
- **IDE**: IntelliJ IDEA / Eclipse（需安装 Lombok 插件）

### 2.2 数据库配置

#### 创建数据库
```sql
CREATE DATABASE city_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

#### 修改配置
编辑 `src/main/resources/application.yaml`：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/city_db
    username: root
    password: 你的密码  # 修改为你的 MySQL 密码
    driver-class-name: com.mysql.cj.jdbc.Driver
```

### 2.3 IDE 配置（重要）

如果使用 IntelliJ IDEA，必须完成以下配置：

1. **安装 Lombok 插件**
   - File → Settings → Plugins → 搜索 "Lombok" → Install

2. **启用注解处理器**
   - File → Settings → Build, Execution, Deployment → Compiler → Annotation Processors
   - ✅ 勾选 "Enable annotation processing"

3. **重新构建项目**
   ```bash
   mvn clean compile
   ```

### 2.4 运行项目

#### 方式一：使用 Maven
```bash
mvn spring-boot:run
```

#### 方式二：打包后运行
```bash
mvn clean package
java -jar target/backend-0.0.1-SNAPSHOT.jar
```

#### 方式三：IDE 直接运行
运行 `BackendApplication.java` 主类

### 2.5 验证启动
启动成功后，访问：
```
http://localhost:8080/city
```

**MCP Server 接口**:
```
http://localhost:8080/api/mcp/health
```

详细 MCP Server 文档请参考 [MCP_SERVER_README.md](MCP_SERVER_README.md)

---

## 📡 API 接口文档

### 3.1 基础信息
- **Base URL**: `http://localhost:8080/city`
- **Content-Type**: `application/json`

### 3.2 接口列表

#### 1. 创建城市
- **接口**: `POST /city`
- **描述**: 新增一个城市记录
- **请求体**:
```json
{
  "name": "北京",
  "population": "2154万"
}
```
- **响应**: 返回创建的城市对象（包含自动生成的 ID）
```json
{
  "id": 1,
  "name": "北京",
  "population": "2154万"
}
```

#### 2. 查询所有城市
- **接口**: `GET /city`
- **描述**: 获取所有城市列表
- **响应**:
```json
[
  {
    "id": 1,
    "name": "北京",
    "population": "2154万"
  },
  {
    "id": 2,
    "name": "上海",
    "population": "2428万"
  }
]
```

#### 3. 根据 ID 查询城市
- **接口**: `GET /city/{id}`
- **描述**: 根据城市 ID 获取详细信息
- **路径参数**: `id` - 城市 ID
- **示例**: `GET /city/1`
- **响应**:
```json
{
  "id": 1,
  "name": "北京",
  "population": "2154万"
}
```
- **异常**: 如果 ID 不存在，返回 500 错误

#### 4. 更新城市
- **接口**: `PUT /city/{id}`
- **描述**: 更新指定 ID 的城市信息
- **路径参数**: `id` - 城市 ID
- **请求体**:
```json
{
  "name": "北京市",
  "population": "2170万"
}
```
- **响应**: 返回更新后的城市对象
- **注意**: 
  - 只更新 `name` 和 `population` 字段
  - `id` 字段不会被修改
  - 如果 ID 不存在，抛出 RuntimeException

#### 5. 删除城市
- **接口**: `DELETE /city/{id}`
- **描述**: 删除指定 ID 的城市记录
- **路径参数**: `id` - 城市 ID
- **示例**: `DELETE /city/1`
- **响应**: 无内容（204 No Content）
- **注意**: 如果 ID 不存在，抛出 RuntimeException

---

## 🔌 MCP Server API 文档

### 3.3 MCP Server 概述

本项目集成了 **MySQL MCP (Model Context Protocol) Server**，提供标准化的 REST API 接口用于安全地访问 MySQL 数据库。MCP Server 特别适用于 AI 模型与数据库的交互场景。

**主要功能**:
- ✅ 列出数据库中的所有表
- ✅ 查询表数据（支持限制条数）
- ✅ 获取表结构信息
- ✅ 执行自定义 SQL 查询（仅 SELECT）
- ✅ 获取数据库统计信息
- ✅ SQL 注入防护和只读访问保证

### 3.4 MCP Server 接口列表

**Base URL**: `http://localhost:8080/api/mcp`

#### 1. 健康检查
```
GET /api/mcp/health
```

#### 2. 列出所有表
```
GET /api/mcp/tables
```

#### 3. 查询表数据
```
GET /api/mcp/table/{tableName}?limit=100
```

#### 4. 获取表结构
```
GET /api/mcp/table/{tableName}/schema
```

#### 5. 执行 SQL 查询
```
POST /api/mcp/query
Content-Type: application/json

{
  "sql": "SELECT * FROM city LIMIT 10"
}
```

#### 6. 获取数据库统计
```
GET /api/mcp/stats
```

**详细文档**: 完整的 MCP Server 使用说明、示例和安全特性请参考 [MCP_SERVER_README.md](MCP_SERVER_README.md)

---

## 🏗️ 架构设计

### 4.1 分层架构

```
┌─────────────────────┐
│   Controller Layer   │  ← REST API 接口，处理 HTTP 请求
│  (CityController)    │
├─────────────────────┤
│   Service Layer      │  ← 业务逻辑，事务管理
│  (CityService)       │
├─────────────────────┤
│ Repository Layer     │  ← 数据访问，JPA 操作
│ (CityRepository)     │
├─────────────────────┤
│   Entity Layer       │  ← 数据模型，映射数据库表
│    (City)            │
└─────────────────────┘
```

### 4.2 核心组件说明

#### City 实体类
```java
@Entity
@Data
public class City {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;           // 主键，自增
    private String name;        // 城市名称
    private String population;  // 人口数量
}
```

**关键注解**:
- `@Entity`: 标识为 JPA 实体
- `@Data`: Lombok 注解，自动生成 getter/setter/toString 等方法
- `@Id`: 标识主键字段
- `@GeneratedValue`: 配置主键生成策略（数据库自增）

#### CityRepository
```java
public interface CityRepository extends JpaRepository<City, Long> {
}
```

**继承的方法**:
- `save()`: 保存或更新实体
- `findById()`: 根据 ID 查询（返回 Optional）
- `findAll()`: 查询所有记录
- `deleteById()`: 根据 ID 删除
- `existsById()`: 检查是否存在

#### CityService
**主要方法**:
- `createCity()`: 创建城市（带事务）
- `getAllCities()`: 查询所有城市
- `getCityById()`: 根据 ID 查询（含存在性检查）
- `updateCity()`: 更新城市信息（带事务）
- `deleteCity()`: 删除城市（带事务和存在性检查）

**事务管理**:
- 使用 `@Transactional` 注解确保数据一致性
- 写操作（创建、更新、删除）都启用了事务

#### CityController
**RESTful 设计**:
- 使用标准 HTTP 方法（GET/POST/PUT/DELETE）
- `@PathVariable`: 从 URL 路径提取参数
- `@RequestBody`: 接收 JSON 请求体
- `@RestController`: 组合注解（@Controller + @ResponseBody）

---

## ⚙️ 配置说明

### 5.1 application.yaml

```yaml
spring:
  application:
    name: backend

  datasource:
    url: jdbc:mysql://localhost:3306/city_db
    username: root
    password: 123456
    driver-class-name: com.mysql.cj.jdbc.Driver

  jpa:
    hibernate:
      ddl-auto: update    # 自动更新表结构
    show-sql: true        # 显示 SQL 语句
```

**JPA 配置项说明**:
- `ddl-auto: update`: 
  - 启动时自动检查并更新数据库表结构
  - 不会删除现有数据
  - 生产环境建议改为 `validate` 或 `none`

- `show-sql: true`: 
  - 在控制台打印执行的 SQL 语句
  - 便于调试和学习
  - 生产环境建议关闭

### 5.2 pom.xml 依赖

**核心依赖**:
- `spring-boot-starter-web`: Web 开发支持
- `spring-boot-starter-data-jpa`: JPA 数据访问
- `mysql-connector-j`: MySQL 驱动
- `lombok`: 代码简化工具
- `spring-boot-devtools`: 开发时热部署

---

## 🔧 常见问题

### 6.1 Lombok 注解飘红

**问题**: `@Data`、`@Entity` 等注解在 IDE 中显示红色错误

**解决方案**:
1. 确认已安装 Lombok 插件
2. 启用 Annotation Processing
3. 执行 `mvn clean compile`
4. 重启 IDE

详细步骤见 [2.3 IDE 配置](#23-ide-配置重要)

### 6.2 数据库连接失败

**问题**: 启动时报错无法连接数据库

**检查清单**:
- ✅ MySQL 服务是否启动
- ✅ 数据库 `city_db` 是否已创建
- ✅ `application.yaml` 中的用户名密码是否正确
- ✅ MySQL 端口（默认 3306）是否可访问

### 6.3 端口冲突

**问题**: 8080 端口已被占用

**解决方案**:
在 `application.yaml` 中添加：
```yaml
server:
  port: 8081  # 修改为其他端口
```

### 6.4 existsById() 使用错误

**问题**: 代码 `cityRepository.existsById(id).isPresent()` 报错

**原因**: `existsById()` 返回 `boolean`，不是 `Optional`

**正确用法**:
```
// ✅ 正确
if (!cityRepository.existsById(id)) {
    throw new RuntimeException("City not found");
}

// ❌ 错误
if (!cityRepository.existsById(id).isPresent()) {  // 编译错误
    ...
}
```

### 6.5 findById() 空指针异常

**问题**: `cityRepository.findById(id).get()` 可能抛出 NoSuchElementException

**推荐做法**:
```
// 方案一：先检查存在性
if (!cityRepository.existsById(id)) {
    throw new RuntimeException("City not found with id: " + id);
}
City city = cityRepository.findById(id).get();

// 方案二：使用 orElseThrow（推荐）
City city = cityRepository.findById(id)
    .orElseThrow(() -> new RuntimeException("City not found with id: " + id));
```

---

## 🎯 最佳实践

### 7.1 异常处理

当前项目使用 `RuntimeException`，建议改进为自定义异常：

```
// 创建自定义异常
public class CityNotFoundException extends RuntimeException {
    public CityNotFoundException(String message) {
        super(message);
    }
}

// 在 Service 中使用
throw new CityNotFoundException("City not found with id: " + id);
```

### 7.2 属性复制优化

当前 `updateCity()` 方法逐个设置属性，可使用 `BeanUtils` 简化：

```
import org.springframework.beans.BeanUtils;

@Transactional
public City updateCity(Long id, City city) {
    City cityToUpdate = cityRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("City not found"));
    
    // 复制非空属性，排除 id
    BeanUtils.copyProperties(city, cityToUpdate, "id");
    return cityRepository.save(cityToUpdate);
}
```

### 7.3 统一响应格式

建议创建统一的 API 响应结构：

```
public class ApiResponse<T> {
    private int code;
    private String message;
    private T data;
    
    // 构造函数、getter、setter
}
```

### 7.4 全局异常处理

添加全局异常处理器：

```
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(CityNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleNotFound(CityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ApiResponse<>(404, ex.getMessage(), null));
    }
}
```

---

## 📊 数据库表结构

### City 表

| 字段名 | 类型 | 约束 | 说明 |
|--------|------|------|------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | 城市 ID |
| name | VARCHAR(255) | NOT NULL | 城市名称 |
| population | VARCHAR(255) | - | 人口数量 |

**建表语句**（由 JPA 自动生成）:
```
CREATE TABLE city (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255),
    population VARCHAR(255),
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

---

## 🧪 测试示例

### 使用 cURL 测试

```
# 1. 创建城市
curl -X POST http://localhost:8080/city \
  -H "Content-Type: application/json" \
  -d '{"name":"北京","population":"2154万"}'

# 2. 查询所有城市
curl http://localhost:8080/city

# 3. 查询单个城市
curl http://localhost:8080/city/1

# 4. 更新城市
curl -X PUT http://localhost:8080/city/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"北京市","population":"2170万"}'

# 5. 删除城市
curl -X DELETE http://localhost:8080/city/1
```

### 使用 Postman 测试

1. 创建 Collection: "City API"
2. 添加上述 5 个请求
3. 设置 Headers: `Content-Type: application/json`
4. 依次执行测试

---

## 🔄 开发流程

### 8.1 添加新功能步骤

1. **定义实体**: 在 `entity` 包创建新实体类
2. **创建 Repository**: 继承 `JpaRepository`
3. **编写 Service**: 实现业务逻辑
4. **创建 Controller**: 暴露 REST API
5. **配置数据库**: 更新 `application.yaml`（如需要）
6. **测试接口**: 使用 cURL 或 Postman

### 8.2 代码规范

- 使用 Lombok 简化代码（`@Data`, `@Builder` 等）
- Service 层的写操作添加 `@Transactional`
- 删除/更新前先检查资源是否存在
- 使用 `Optional` 处理可能为空的结果
- REST API 遵循 RESTful 设计规范

---

## 📝 更新日志

### v0.0.1-SNAPSHOT (当前版本)
- ✅ 实现城市信息的 CRUD 功能
- ✅ 集成 Spring Data JPA
- ✅ 配置 MySQL 数据库
- ✅ 使用 Lombok 简化代码
- ✅ 添加事务管理
- ✅ 实现存在性检查

---

## 🤝 贡献指南

欢迎提交 Issue 和 Pull Request！

### 提交 Issue
- 清晰描述问题
- 提供复现步骤
- 附上错误日志

### 提交 PR
1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 开启 Pull Request

---

## 📄 许可证

本项目仅供学习参考使用。

---

## 📞 联系方式

如有问题，请提交 Issue 或联系项目维护者。

---

**最后更新时间**: 2026-07-08
