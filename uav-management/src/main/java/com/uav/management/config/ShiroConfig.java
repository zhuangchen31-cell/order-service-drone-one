package com.uav.management.config;

import com.uav.management.realm.UavUserRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    /**
     * 配置Realm（含 MD5 凭证匹配器以支持数据库存储的哈希密码）
     */
    @Bean
    public UavUserRealm userRealm() {
        UavUserRealm realm = new UavUserRealm();
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("MD5");
        matcher.setHashIterations(1);
        realm.setCredentialsMatcher(matcher);
        return realm;
    }

    /**
     * 配置SecurityManager
     */
    @Bean
    public DefaultWebSecurityManager securityManager(UavUserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    /**
     * 配置过滤器链
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);

        // 配置登录页面
        shiroFilter.setLoginUrl("/login");
        // 配置成功页面
        shiroFilter.setSuccessUrl("/drone/list");
        // 配置未授权页面
        shiroFilter.setUnauthorizedUrl("/unauthorized");

        // 配置过滤器链（顺序敏感：优先匹配的规则在前）
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 匿名可访问：登录页、登出、静态资源
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/logout", "anon");
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/images/**", "anon");
        // 需要认证才能访问
        filterChainDefinitionMap.put("/drone/**", "authc");
        filterChainDefinitionMap.put("/", "authc");
        // 需要admin角色
        filterChainDefinitionMap.put("/admin/**", "roles[ADMIN]");
        // 其他所有请求需要认证
        filterChainDefinitionMap.put("/**", "authc");

        shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilter;
    }
}
