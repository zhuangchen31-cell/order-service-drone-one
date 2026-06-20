package com.uav.management.realm;

import com.uav.management.entity.User;
import com.uav.management.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class UavUserRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(UavUserRealm.class);

    @Autowired
    private UserService userService;

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Object primaryPrincipal = principals.getPrimaryPrincipal();
        if (primaryPrincipal instanceof User) {
            User user = (User) primaryPrincipal;
            authorizationInfo.addRole(user.getRole());
        }
        return authorizationInfo;
    }

    /**
     * 认证：优先从数据库查询用户，数据库无数据时回退到默认管理员账号
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();

        // 优先从数据库查询用户
        try {
            User user = userService.findByUsername(username);
            if (user != null) {
                if ("LOCKED".equals(user.getStatus())) {
                    throw new LockedAccountException("账号已锁定");
                }
                return new SimpleAuthenticationInfo(
                        user,
                        user.getPassword(),
                        getName()
                );
            }
        } catch (Exception e) {
            logger.warn("数据库查询用户失败，回退到默认账号: {}", e.getMessage());
        }

        // 数据库无此用户时，回退到默认管理员账号（开发/演示环境）
        if ("admin".equals(username)) {
            User adminUser = new User();
            adminUser.setId(1L);
            adminUser.setUsername("admin");
            adminUser.setPassword("admin123");
            adminUser.setSalt("");
            adminUser.setRole("ADMIN");
            adminUser.setStatus("ACTIVE");

            return new SimpleAuthenticationInfo(
                    adminUser,
                    adminUser.getPassword(),
                    getName()
            );
        }

        throw new UnknownAccountException("用户名或密码错误");
    }
}
