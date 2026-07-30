package com.demo.backend.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * MCP Server 配置类
 * 用于配置 Model Context Protocol 服务器
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class McpServerConfig {

    private final JdbcTemplate jdbcTemplate;

    /**
     * 配置 MCP Server 相关的 Bean
     */
    @Bean
    public MySqlResourceProvider mySqlResourceProvider() {
        return new MySqlResourceProvider(jdbcTemplate);
    }
}
