# MySQL MCP Server 使用示例

## 🚀 快速开始

### 1. 启动应用

```bash
mvn spring-boot:run
```

### 2. 验证服务

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

## 📝 使用示例

### 示例 1: 列出所有表

```bash
curl http://localhost:8080/api/mcp/tables
```

响应：
```json
{
  "success": true,
  "tables": ["city", "user"],
  "count": 2
}
```

### 示例 2: 查询 city 表数据

```bash
# 查询前 10 条记录
curl "http://localhost:8080/api/mcp/table/city?limit=10"

# 查询前 50 条记录
curl "http://localhost:8080/api/mcp/table/city?limit=50"
```

响应：
```json
{
  "success": true,
  "table": "city",
  "data": [
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
  ],
  "count": 2
}
```

### 示例 3: 获取表结构

```bash
curl http://localhost:8080/api/mcp/table/city/schema
```

响应：
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
    },
    {
      "Field": "population",
      "Type": "varchar(255)",
      "Null": "YES",
      "Key": "",
      "Default": null,
      "Extra": ""
    }
  ]
}
```

### 示例 4: 执行自定义 SQL 查询

```bash
curl -X POST http://localhost:8080/api/mcp/query \
  -H "Content-Type: application/json" \
  -d '{
    "sql": "SELECT * FROM city WHERE id < 10"
  }'
```

响应：
```json
{
  "success": true,
  "data": [
    {"id": 1, "name": "北京", "population": "2154万"},
    {"id": 2, "name": "上海", "population": "2428万"}
  ],
  "count": 2
}
```

**注意**: 只支持 SELECT 语句，以下操作会被拒绝：
- ❌ `INSERT INTO ...`
- ❌ `UPDATE ...`
- ❌ `DELETE ...`
- ❌ `DROP TABLE ...`

### 示例 5: 获取数据库统计信息

```bash
curl http://localhost:8080/api/mcp/stats
```

响应：
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

---

## 🔒 安全特性演示

### SQL 注入防护

尝试非法表名会被拒绝：

```bash
# ❌ 错误示例：包含特殊字符
curl "http://localhost:8080/api/mcp/table/city;DROP%20TABLE%20city"

# 响应：
{
  "success": false,
  "error": "Failed to query table: city;DROP TABLE city"
}
```

### 只读访问保证

尝试写操作会被拒绝：

```bash
# ❌ 错误示例：尝试删除数据
curl -X POST http://localhost:8080/api/mcp/query \
  -H "Content-Type: application/json" \
  -d '{
    "sql": "DELETE FROM city WHERE id = 1"
  }'

# 响应：
{
  "success": false,
  "error": "Only SELECT statements are allowed"
}
```

---

## 💻 Java 客户端示例

### 使用 RestTemplate

```java
@RestController
public class DemoController {
    
    @Autowired
    private RestTemplate restTemplate;
    
    private static final String MCP_BASE_URL = "http://localhost:8080/api/mcp";
    
    @GetMapping("/demo/tables")
    public Map<String, Object> getTables() {
        return restTemplate.getForObject(MCP_BASE_URL + "/tables", Map.class);
    }
    
    @GetMapping("/demo/city-data")
    public Map<String, Object> getCityData() {
        String url = MCP_BASE_URL + "/table/city?limit=10";
        return restTemplate.getForObject(url, Map.class);
    }
    
    @GetMapping("/demo/custom-query")
    public Map<String, Object> customQuery() {
        String url = MCP_BASE_URL + "/query";
        
        Map<String, String> request = new HashMap<>();
        request.put("sql", "SELECT * FROM city LIMIT 5");
        
        return restTemplate.postForObject(url, request, Map.class);
    }
}
```

### 使用 WebClient（响应式）

```java
@Service
public class McpService {
    
    private final WebClient webClient;
    
    public McpService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
            .baseUrl("http://localhost:8080/api/mcp")
            .build();
    }
    
    public Mono<Map> getTables() {
        return webClient.get()
            .uri("/tables")
            .retrieve()
            .bodyToMono(Map.class);
    }
    
    public Mono<Map> queryTable(String tableName, int limit) {
        return webClient.get()
            .uri("/table/{table}?limit={limit}", tableName, limit)
            .retrieve()
            .bodyToMono(Map.class);
    }
    
    public Mono<Map> executeQuery(String sql) {
        Map<String, String> request = Map.of("sql", sql);
        
        return webClient.post()
            .uri("/query")
            .bodyValue(request)
            .retrieve()
            .bodyToMono(Map.class);
    }
}
```

---

## 🌐 前端 JavaScript 示例

### 使用 Fetch API

```javascript
// 获取所有表
async function getTables() {
  const response = await fetch('http://localhost:8080/api/mcp/tables');
  const data = await response.json();
  console.log('Tables:', data);
  return data;
}

// 查询表数据
async function queryTable(tableName, limit = 100) {
  const response = await fetch(
    `http://localhost:8080/api/mcp/table/${tableName}?limit=${limit}`
  );
  const data = await response.json();
  console.log('Data:', data);
  return data;
}

// 执行自定义查询
async function executeQuery(sql) {
  const response = await fetch('http://localhost:8080/api/mcp/query', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ sql }),
  });
  const data = await response.json();
  console.log('Query Result:', data);
  return data;
}

// 使用示例
getTables();
queryTable('city', 10);
executeQuery('SELECT * FROM city WHERE id < 5');
```

### 使用 Axios

```javascript
import axios from 'axios';

const mcpClient = axios.create({
  baseURL: 'http://localhost:8080/api/mcp',
});

// 获取所有表
const tables = await mcpClient.get('/tables');
console.log(tables.data);

// 查询表数据
const cityData = await mcpClient.get('/table/city', {
  params: { limit: 10 }
});
console.log(cityData.data);

// 执行自定义查询
const result = await mcpClient.post('/query', {
  sql: 'SELECT * FROM city LIMIT 5'
});
console.log(result.data);
```

---

## 🐍 Python 示例

```python
import requests

MCP_BASE_URL = "http://localhost:8080/api/mcp"

# 获取所有表
def get_tables():
    response = requests.get(f"{MCP_BASE_URL}/tables")
    return response.json()

# 查询表数据
def query_table(table_name, limit=100):
    response = requests.get(
        f"{MCP_BASE_URL}/table/{table_name}",
        params={"limit": limit}
    )
    return response.json()

# 执行自定义查询
def execute_query(sql):
    response = requests.post(
        f"{MCP_BASE_URL}/query",
        json={"sql": sql}
    )
    return response.json()

# 使用示例
if __name__ == "__main__":
    # 获取表列表
    tables = get_tables()
    print("Tables:", tables)
    
    # 查询 city 表
    cities = query_table("city", 10)
    print("Cities:", cities)
    
    # 自定义查询
    result = execute_query("SELECT * FROM city WHERE id < 5")
    print("Query Result:", result)
```

---

## 🔧 Postman 集合

可以导入以下 Postman 集合进行测试：

```json
{
  "info": {
    "name": "MySQL MCP Server",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Health Check",
      "request": {
        "method": "GET",
        "header": [],
        "url": {
          "raw": "http://localhost:8080/api/mcp/health",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["api", "mcp", "health"]
        }
      }
    },
    {
      "name": "List Tables",
      "request": {
        "method": "GET",
        "header": [],
        "url": {
          "raw": "http://localhost:8080/api/mcp/tables",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["api", "mcp", "tables"]
        }
      }
    },
    {
      "name": "Query City Table",
      "request": {
        "method": "GET",
        "header": [],
        "url": {
          "raw": "http://localhost:8080/api/mcp/table/city?limit=10",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["api", "mcp", "table", "city"],
          "query": [
            {
              "key": "limit",
              "value": "10"
            }
          ]
        }
      }
    },
    {
      "name": "Custom Query",
      "request": {
        "method": "POST",
        "header": [
          {
            "key": "Content-Type",
            "value": "application/json"
          }
        ],
        "body": {
          "mode": "raw",
          "raw": "{\n  \"sql\": \"SELECT * FROM city LIMIT 5\"\n}"
        },
        "url": {
          "raw": "http://localhost:8080/api/mcp/query",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["api", "mcp", "query"]
        }
      }
    }
  ]
}
```

---

## ⚠️ 注意事项

1. **安全性**: 
   - 生产环境务必添加认证机制
   - 配置 CORS 限制访问来源
   - 启用 HTTPS

2. **性能优化**:
   - 合理设置查询限制（limit）
   - 考虑添加缓存层（Redis）
   - 监控慢查询

3. **错误处理**:
   - 检查响应的 `success` 字段
   - 处理网络异常
   - 实现重试机制

4. **最佳实践**:
   - 避免一次性加载大量数据
   - 使用参数化查询防止 SQL 注入
   - 定期审计查询日志

---

**更多详细信息请参考**: [MCP_SERVER_README.md](../MCP_SERVER_README.md)
