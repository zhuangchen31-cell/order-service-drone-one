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
     * 执行 SQL 文件（逐行读取，按分号分隔语句，跳过注释和空行）
     */
    private void executeSqlFile(String fileName) throws Exception {
        logger.info("执行 SQL 文件: {}", fileName);

        ClassPathResource resource = new ClassPathResource(fileName);
        if (!resource.exists()) {
            logger.warn("SQL 文件不存在，跳过: {}", fileName);
            return;
        }

        try (InputStream inputStream = resource.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            StringBuilder currentStatement = new StringBuilder();
            String line;
            int statementCount = 0;

            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();
                // 跳过注释行和空行
                if (trimmed.isEmpty() || trimmed.startsWith("--")) {
                    continue;
                }
                currentStatement.append(line).append("\n");
                // 遇到分号表示语句结束
                if (trimmed.endsWith(";")) {
                    String sql = currentStatement.toString().trim();
                    // 去掉末尾分号
                    sql = sql.substring(0, sql.length() - 1);
                    if (!sql.isEmpty()) {
                        try {
                            jdbcTemplate.execute(sql);
                            statementCount++;
                            logger.debug("SQL 执行成功 ({}): {}", statementCount,
                                sql.length() > 80 ? sql.substring(0, 80) + "..." : sql);
                        } catch (Exception e) {
                            logger.warn("SQL 执行失败(可能已存在): {} - 原因: {}",
                                sql.length() > 80 ? sql.substring(0, 80) + "..." : sql,
                                e.getMessage());
                        }
                    }
                    currentStatement = new StringBuilder();
                }
            }

            // 处理末尾没有分号的语句
            String remaining = currentStatement.toString().trim();
            if (!remaining.isEmpty()) {
                try {
                    jdbcTemplate.execute(remaining);
                    statementCount++;
                } catch (Exception e) {
                    logger.warn("SQL 执行失败(可能已存在): {} - 原因: {}",
                        remaining.length() > 80 ? remaining.substring(0, 80) + "..." : remaining,
                        e.getMessage());
                }
            }

            logger.info("SQL 文件 {} 执行完成，共 {} 条语句", fileName, statementCount);
        }
    }
}