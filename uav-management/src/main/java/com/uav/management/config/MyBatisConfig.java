package com.uav.management.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.uav.management.mapper")
public class MyBatisConfig {

    /**
     * 配置SqlSessionFactory
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setMapperLocations(
            new PathMatchingResourcePatternResolver()
                .getResources("classpath:mapper/*.xml")
        );
        // 配置数据库方言提供者
        factoryBean.setDatabaseIdProvider(databaseIdProvider());
        return factoryBean.getObject();
    }

    /**
     * 配置数据库方言提供者
     */
    @Bean
    public org.apache.ibatis.mapping.DatabaseIdProvider databaseIdProvider() {
        org.apache.ibatis.mapping.VendorDatabaseIdProvider provider = new org.apache.ibatis.mapping.VendorDatabaseIdProvider();
        provider.setProperties(getDatabaseIdProperties());
        return provider;
    }

    /**
     * 获取数据库方言属性
     */
    private java.util.Properties getDatabaseIdProperties() {
        java.util.Properties properties = new java.util.Properties();
        properties.setProperty("SQLite", "sqlite");
        properties.setProperty("MySQL", "mysql");
        return properties;
    }
}
