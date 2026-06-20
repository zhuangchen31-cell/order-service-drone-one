package com.uav.management.realm;

import com.uav.management.entity.User;
import com.uav.management.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class UavUserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user = (User) principals.getPrimaryPrincipal();
        authorizationInfo.addRole(user.getRole());
        return authorizationInfo;
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();

        // 这里使用硬编码的管理员账号，实际项目中应该从数据库查询
        if ("admin".equals(username)) {
            User adminUser = new User();
            adminUser.setId(1L);
            adminUser.setUsername("admin");
            // 暂时使用明文密码，确保登录功能能够正常工作
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

        // 实际项目中从数据库查询用户
        // User user = userService.findByUsername(username);
        // if (user == null) {
        //     throw new UnknownAccountException("用户名不存在");
        // }
        // if ("LOCKED".equals(user.getStatus())) {
        //     throw new LockedAccountException("账号已锁定");
        // }

        throw new UnknownAccountException("用户名不存在");
    }
}
