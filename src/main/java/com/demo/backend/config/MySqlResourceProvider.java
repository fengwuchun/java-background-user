package com.demo.backend.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MySQL 资源提供者
 * 提供数据库表和查询结果的访问能力
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MySqlResourceProvider {

    private final JdbcTemplate jdbcTemplate;

    /**
     * 获取所有可用的表名
     */
    public List<String> getTableNames() {
        try {
            return jdbcTemplate.queryForList("SHOW TABLES", String.class);
        } catch (Exception e) {
            log.error("Failed to get table names", e);
            return new ArrayList<>();
        }
    }

    /**
     * 查询表数据
     * @param tableName 表名
     * @param limit 限制条数
     * @return 查询结果
     */
    public List<Map<String, Object>> queryTable(String tableName, int limit) {
        try {
            // 防止 SQL 注入，验证表名
            if (!tableName.matches("^[a-zA-Z0-9_]+$")) {
                throw new IllegalArgumentException("Invalid table name");
            }
            
            String sql = "SELECT * FROM " + tableName + " LIMIT ?";
            return jdbcTemplate.queryForList(sql, limit);
        } catch (Exception e) {
            log.error("Failed to query table: {}", tableName, e);
            throw new RuntimeException("Failed to query table: " + tableName, e);
        }
    }

    /**
     * 执行自定义 SQL 查询（只读）
     * @param sql SQL 语句
     * @return 查询结果
     */
    public List<Map<String, Object>> executeQuery(String sql) {
        try {
            // 安全检查：只允许 SELECT 语句
            String trimmedSql = sql.trim().toUpperCase();
            if (!trimmedSql.startsWith("SELECT")) {
                throw new IllegalArgumentException("Only SELECT statements are allowed");
            }
            
            return jdbcTemplate.queryForList(sql);
        } catch (Exception e) {
            log.error("Failed to execute query: {}", sql, e);
            throw new RuntimeException("Failed to execute query", e);
        }
    }

    /**
     * 获取表结构信息
     * @param tableName 表名
     * @return 表结构信息
     */
    public List<Map<String, Object>> getTableSchema(String tableName) {
        try {
            if (!tableName.matches("^[a-zA-Z0-9_]+$")) {
                throw new IllegalArgumentException("Invalid table name");
            }
            
            String sql = "DESCRIBE " + tableName;
            return jdbcTemplate.queryForList(sql);
        } catch (Exception e) {
            log.error("Failed to get table schema: {}", tableName, e);
            throw new RuntimeException("Failed to get table schema", e);
        }
    }

    /**
     * 获取数据库统计信息
     * @return 统计信息
     */
    public Map<String, Object> getDatabaseStats() {
        Map<String, Object> stats = new HashMap<>();
        
        try {
            List<String> tables = getTableNames();
            stats.put("totalTables", tables.size());
            stats.put("tables", tables);
            
            // 获取每个表的行数
            Map<String, Long> tableRows = new HashMap<>();
            for (String table : tables) {
                try {
                    Long count = jdbcTemplate.queryForObject(
                            "SELECT COUNT(*) FROM " + table, 
                            Long.class
                    );
                    tableRows.put(table, count);
                } catch (Exception e) {
                    log.warn("Failed to get row count for table: {}", table);
                }
            }
            stats.put("tableRows", tableRows);
            
        } catch (Exception e) {
            log.error("Failed to get database stats", e);
        }
        
        return stats;
    }
}
