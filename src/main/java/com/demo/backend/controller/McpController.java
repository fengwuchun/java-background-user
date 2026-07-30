package com.demo.backend.controller;

import com.demo.backend.config.MySqlResourceProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MCP Server HTTP 端点控制器
 * 提供 MySQL 数据访问的 REST API 接口
 */
@Slf4j
@RestController
@RequestMapping("/api/mcp")
@RequiredArgsConstructor
public class McpController {

    private final MySqlResourceProvider mySqlResourceProvider;

    /**
     * 获取所有可用的表名
     */
    @GetMapping("/tables")
    public ResponseEntity<Map<String, Object>> listTables() {
        try {
            List<String> tables = mySqlResourceProvider.getTableNames();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("tables", tables);
            response.put("count", tables.size());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Failed to list tables", e);
            return ResponseEntity.internalServerError().body(
                    Map.of("success", false, "error", e.getMessage())
            );
        }
    }

    /**
     * 查询表数据
     */
    @GetMapping("/table/{tableName}")
    public ResponseEntity<Map<String, Object>> queryTable(
            @PathVariable String tableName,
            @RequestParam(defaultValue = "100") int limit) {
        try {
            List<Map<String, Object>> data = mySqlResourceProvider.queryTable(tableName, limit);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("table", tableName);
            response.put("data", data);
            response.put("count", data.size());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Failed to query table: {}", tableName, e);
            return ResponseEntity.internalServerError().body(
                    Map.of("success", false, "error", e.getMessage())
            );
        }
    }

    /**
     * 获取表结构
     */
    @GetMapping("/table/{tableName}/schema")
    public ResponseEntity<Map<String, Object>> getTableSchema(@PathVariable String tableName) {
        try {
            List<Map<String, Object>> schema = mySqlResourceProvider.getTableSchema(tableName);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("table", tableName);
            response.put("schema", schema);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Failed to get table schema: {}", tableName, e);
            return ResponseEntity.internalServerError().body(
                    Map.of("success", false, "error", e.getMessage())
            );
        }
    }

    /**
     * 执行自定义 SQL 查询
     */
    @PostMapping("/query")
    public ResponseEntity<Map<String, Object>> executeQuery(@RequestBody Map<String, String> request) {
        try {
            String sql = request.get("sql");
            if (sql == null || sql.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(
                        Map.of("success", false, "error", "SQL statement is required")
                );
            }
            
            List<Map<String, Object>> data = mySqlResourceProvider.executeQuery(sql);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", data);
            response.put("count", data.size());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Failed to execute query", e);
            return ResponseEntity.internalServerError().body(
                    Map.of("success", false, "error", e.getMessage())
            );
        }
    }

    /**
     * 获取数据库统计信息
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getDatabaseStats() {
        try {
            Map<String, Object> stats = mySqlResourceProvider.getDatabaseStats();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("stats", stats);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Failed to get database stats", e);
            return ResponseEntity.internalServerError().body(
                    Map.of("success", false, "error", e.getMessage())
            );
        }
    }

    /**
     * 健康检查
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "ok");
        response.put("service", "MySQL MCP Server");
        response.put("version", "1.0.0");
        
        return ResponseEntity.ok(response);
    }
}
