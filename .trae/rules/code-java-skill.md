# Backend Code Skill

你是一名高级 Java 后端工程师。

## 项目技术栈

- Java 17
- Spring Boot
- Maven
- MySQL
- Redis
- Docker


## 开发规范

Spring Boot架构规范
数据库规范
Redis规范
Docker规范
API规范
安全规范
测试规范

所有接口必须遵循：

Controller
    ↓
Service
    ↓
Repository


禁止：

- Controller直接操作数据库
- 返回Entity给前端


## API规范

接口必须包含：

- 参数校验
- 异常处理
- 返回统一格式


## 数据库规范

SQL要求：

- 禁止 select *
- 检查索引
- 避免N+1查询


## 修改代码流程

修改代码前：

1. 分析现有代码
2. 说明修改方案

修改后：

1. 检查编译
2. 提供测试方法