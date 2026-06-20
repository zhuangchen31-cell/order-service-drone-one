package com.uav.management.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * 数据库初始化器
 * 在应用启动时执行SQL初始化脚本
 */
@Component
public class DatabaseInitializer implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseInitializer.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("开始初始化数据库...");
        
        try {
            // 执行 schema.sql
            executeSqlFile("schema.sql");
            
            // 执行 data.sql
            executeSqlFile("data.sql");
            
            logger.info("数据库初始化完成");
        } catch (Exception e) {
            logger.error("数据库初始化失败", e);
            throw e;
        }
    }

    /**
     * 执行 SQL 文件
     */
    private void executeSqlFile(String fileName) throws Exception {
        logger.info("执行 SQL 文件: {}", fileName);
        
        ClassPathResource resource = new ClassPathResource(fileName);
        try (InputStream inputStream = resource.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            
            String sqlContent = reader.lines().collect(Collectors.joining("\n"));
            
            // 分割 SQL 语句
            String[] statements = sqlContent.split(";\\s*");
            
            for (String statement : statements) {
                if (!statement.trim().isEmpty()) {
                    try {
                        jdbcTemplate.execute(statement);
                        logger.debug("执行 SQL: {}", statement);
                    } catch (Exception e) {
                        logger.warn("执行 SQL 语句失败(可能已存在): {}", statement);
                    }
                }
            }
        }
    }
}