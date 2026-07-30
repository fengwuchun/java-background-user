# JWT 技术说明

## 1. 作用

本项目使用 JWT（JSON Web Token）实现用户登录后无状态认证。

- 登录成功后生成 token 返回给客户端
- 客户端后续请求在 `Authorization: Bearer <token>` 头中携带 token
- 服务端通过 JWT 过滤器验证 token 并设置 Spring Security 上下文

## 2. 关键类

### `JwtUtil`（`src/main/java/com/demo/backend/Utils/JwtUtil.java`）

负责 JWT 的生成与解析：

- `createToken(String username, String role)`
  - 使用 `io.jsonwebtoken.Jwts` 构建 JWT
  - `sub` 字段保存用户名
  - 自定义 claim `role` 保存用户角色
  - 过期时间为 10 小时
  - 使用 `HS256` 算法签名

- `parseUsername(String token)`
  - 解析 token 并返回 `sub`（用户名）

- `parseRole(String token)`
  - 解析 token 并返回 `role`

> 注意：当前 `SECRET_KEY` 是硬编码在 `JwtUtil` 中，长度必须超过 32 字节以满足 `Keys.hmacShaKeyFor()` 要求。

## 3. 登录流程

### `UserController.login`（`src/main/java/com/demo/backend/User/controller/UserController.java`）

- 接收 POST `/user/auth/login`
- 根据请求体中的 `email` 和 `password` 查询数据库用户
- 校验成功后调用 `JwtUtil.createToken(username, role)`
- 返回封装后的 `HttpResult.success(token)`

### 允许登录接口匿名访问

### `SecurityConfig`（`src/main/java/com/demo/backend/security/SecurityConfig.java`）

当前安全配置：

- 禁用 CSRF
- 设置 `SessionCreationPolicy.STATELESS`
- 允许 `/user/auth/login` 匿名访问
- 其他请求必须认证
- 在 `UsernamePasswordAuthenticationFilter` 之前添加 `JwtFilter`

## 4. JWT 校验过滤器

### `JwtFilter`（`src/main/java/com/demo/backend/security/JwtFilter.java`）

过滤器逻辑：

- 如果请求路径是 `/user/auth/login` 或 `/user/auth/register`，直接放行
- 读取请求头 `Authorization`
- 如果以 `Bearer ` 开头：
  - 取出 token
  - 解析用户名与角色
  - 构建 `UsernamePasswordAuthenticationToken`
  - 将认证信息写入 `SecurityContextHolder`
- token 解析失败时返回 `401 Unauthorized` JSON 响应

## 5. 角色与权限

- JWT 中通过 `role` claim 保存用户角色
- 过滤器会将角色转换为 `ROLE_<role>` 权限
- 例如：`ROLE_ADMIN`
- 控制器中可使用 Spring Security 注解进行权限控制，例如：
  - `@PreAuthorize("hasRole('ADMIN')")`

## 6. 项目使用场景

- 登录接口：`POST /user/auth/login`
- 受保护接口：除登录外的所有接口均需携带合法 JWT
- 示例受保护接口：
  - `GET /user/{id}`
  - `GET /user/list`
  - `DELETE /user/{id}`（需要 `ADMIN` 角色）

## 7. 注意事项

- `SECRET_KEY` 不要直接硬编码到代码中，建议改为配置文件或环境变量
- 生产环境应使用更复杂的密钥并妥善保管
- token 一旦签名后不可修改，否则解析失败
- 已经过期的 token 会自动失效
- 若需要强制注销，可考虑 token 黑名单或短期 token + 刷新 token

## 8. 进一步优化建议

- 将 `JwtUtil` 的 `SECRET_KEY` 移动到 `application.yaml` 或环境变量
- 增加 token 刷新接口
- 支持更多标准 claims（如 `iat`, `iss`, `aud`）
- 将角色权限从数据库动态加载，而不是硬编码 `ROLE_` 前缀
