# MySQL MCP Server

本项目已集成 MySQL MCP (Model Context Protocol) Server，提供标准的 REST API 接口用于访问 MySQL 数据库。

## 功能特性

- ✅ 列出数据库中的所有表
- ✅ 查询表数据（支持限制条数）
- ✅ 获取表结构信息
- ✅ 执行自定义 SQL 查询（仅支持 SELECT）
- ✅ 获取数据库统计信息
- ✅ SQL 注入防护
- ✅ 只读访问保证

## API 端点

### 1. 健康检查
```
GET /api/mcp/health
```

**响应示例：**
```json
{
  "status": "ok",
  "service": "MySQL MCP Server",
  "version": "1.0.0"
}
```

### 2. 列出所有表
```
GET /api/mcp/tables
```

**响应示例：**
```json
{
  "success": true,
  "tables": ["city", "user"],
  "count": 2
}
```

### 3. 查询表数据
```
GET /api/mcp/table/{tableName}?limit=100
```

**参数：**
- `tableName`: 表名（路径参数）
- `limit`: 限制返回条数（可选，默认 100）

**示例：**
```
GET /api/mcp/table/city?limit=50
```

**响应示例：**
```json
{
  "success": true,
  "table": "city",
  "data": [
    {"id": 1, "name": "Beijing", "population": 21540000},
    {"id": 2, "name": "Shanghai", "population": 24280000}
  ],
  "count": 2
}
```

### 4. 获取表结构
```
GET /api/mcp/table/{tableName}/schema
```

**示例：**
```
GET /api/mcp/table/city/schema
```

**响应示例：**
```json
{
  "success": true,
  "table": "city",
  "schema": [
    {
      "Field": "id",
      "Type": "bigint",
      "Null": "NO",
      "Key": "PRI",
      "Default": null,
      "Extra": "auto_increment"
    },
    {
      "Field": "name",
      "Type": "varchar(255)",
      "Null": "YES",
      "Key": "",
      "Default": null,
      "Extra": ""
    }
  ]
}
```

### 5. 执行自定义 SQL 查询
```
POST /api/mcp/query
Content-Type: application/json

{
  "sql": "SELECT * FROM city WHERE population > 10000000"
}
```

**注意：** 仅支持 SELECT 语句，其他操作会被拒绝。

**响应示例：**
```json
{
  "success": true,
  "data": [
    {"id": 1, "name": "Beijing", "population": 21540000}
  ],
  "count": 1
}
```

### 6. 获取数据库统计信息
```
GET /api/mcp/stats
```

**响应示例：**
```json
{
  "success": true,
  "stats": {
    "totalTables": 2,
    "tables": ["city", "user"],
    "tableRows": {
      "city": 100,
      "user": 50
    }
  }
}
```

## 安全特性

1. **SQL 注入防护**：表名经过正则表达式验证，只允许字母、数字和下划线
2. **只读访问**：自定义查询仅允许 SELECT 语句
3. **查询限制**：表查询默认限制 100 条记录，防止大数据量返回

## 配置说明

在 `application.yaml` 中配置数据库连接：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/city_db
    username: root
    password: 123456
    driver-class-name: com.mysql.cj.jdbc.Driver

mcp:
  server:
    name: mysql-mcp-server
    version: 1.0.0
    enabled: true
```

## 启动服务

```bash
mvn spring-boot:run
```

服务将在 `http://localhost:8080` 启动。

## 使用示例

### cURL 示例

```bash
# 健康检查
curl http://localhost:8080/api/mcp/health

# 列出所有表
curl http://localhost:8080/api/mcp/tables

# 查询 city 表前 10 条数据
curl http://localhost:8080/api/mcp/table/city?limit=10

# 获取 city 表结构
curl http://localhost:8080/api/mcp/table/city/schema

# 执行自定义查询
curl -X POST http://localhost:8080/api/mcp/query \
  -H "Content-Type: application/json" \
  -d '{"sql": "SELECT * FROM city LIMIT 5"}'

# 获取数据库统计
curl http://localhost:8080/api/mcp/stats
```

### Java 客户端示例

```java
@RestController
public class DemoController {
    
    @Autowired
    private RestTemplate restTemplate;
    
    @GetMapping("/demo")
    public Map<String, Object> demo() {
        // 查询 city 表
        String url = "http://localhost:8080/api/mcp/table/city?limit=10";
        return restTemplate.getForObject(url, Map.class);
    }
}
```

## 技术栈

- Spring Boot 4.1.0
- Spring JDBC
- MySQL Connector
- Lombok

## 注意事项

1. 确保 MySQL 数据库已启动并可访问
2. 数据库用户需要有相应的查询权限
3. MCP Server 仅提供只读访问，不支持写操作
4. 生产环境建议添加认证和授权机制
5. 建议配置连接池以优化性能

## 后续扩展

可以进一步扩展的功能：
- 添加认证中间件（JWT、OAuth2 等）
- 实现查询缓存（Redis）
- 添加查询审计日志
- 支持更多数据库类型（PostgreSQL、Oracle 等）
- 实现完整的 MCP 协议规范
- 添加速率限制和配额管理
