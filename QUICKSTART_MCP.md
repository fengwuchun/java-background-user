# MCP Server 快速启动指南

## 🎯 5 分钟快速开始

### 前置条件
- ✅ JDK 17+ 已安装
- ✅ Maven 3.6+ 已安装
- ✅ MySQL 数据库已运行
- ✅ 数据库 `city_db` 已创建

---

## 📋 步骤 1: 检查数据库配置

确保 `src/main/resources/application.yaml` 中的数据库配置正确：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/city_db
    username: root
    password: 123456  # 修改为你的密码
```

---

## 🚀 步骤 2: 启动应用

在项目根目录执行：

```bash
mvn spring-boot:run
```

等待看到以下日志表示启动成功：
```
Started BackendApplication in X.XXX seconds
```

---

## ✅ 步骤 3: 验证服务

打开新终端，测试健康检查接口：

```bash
curl http://localhost:8080/api/mcp/health
```

预期响应：
```json
{
  "status": "ok",
  "service": "MySQL MCP Server",
  "version": "1.0.0"
}
```

---

## 🧪 步骤 4: 测试功能

### 测试 1: 列出所有表
```bash
curl http://localhost:8080/api/mcp/tables
```

### 测试 2: 查询 city 表
```bash
curl "http://localhost:8080/api/mcp/table/city?limit=5"
```

### 测试 3: 获取表结构
```bash
curl http://localhost:8080/api/mcp/table/city/schema
```

### 测试 4: 自定义查询
```bash
curl -X POST http://localhost:8080/api/mcp/query \
  -H "Content-Type: application/json" \
  -d '{"sql": "SELECT COUNT(*) as total FROM city"}'
```

---

## 🎨 浏览器测试

直接在浏览器中访问：

- 健康检查: http://localhost:8080/api/mcp/health
- 表列表: http://localhost:8080/api/mcp/tables
- City 表数据: http://localhost:8080/api/mcp/table/city?limit=10
- 数据库统计: http://localhost:8080/api/mcp/stats

---

## 🔍 常见问题

### Q1: 端口 8080 被占用怎么办？

在 `application.yaml` 中添加：
```yaml
server:
  port: 8081
```

### Q2: 数据库连接失败？

检查：
1. MySQL 服务是否启动
2. 数据库 `city_db` 是否存在
3. 用户名密码是否正确

### Q3: 没有数据返回？

确保数据库中有数据，可以先通过原有的 City API 添加数据：
```bash
curl -X POST http://localhost:8080/city \
  -H "Content-Type: application/json" \
  -d '{"name":"北京","population":"2154万"}'
```

---

## 📚 下一步

- 📖 查看完整文档: [MCP_SERVER_README.md](MCP_SERVER_README.md)
- 💡 查看更多示例: [docs/MCP_EXAMPLES.md](docs/MCP_EXAMPLES.md)
- 📝 了解集成详情: [MCP_INTEGRATION_SUMMARY.md](MCP_INTEGRATION_SUMMARY.md)

---

**就这么简单！** 🎉
