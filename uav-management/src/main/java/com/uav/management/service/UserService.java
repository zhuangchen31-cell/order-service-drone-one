package com.uav.management.service;

import com.uav.management.entity.User;

public interface UserService {

    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户信息
     */
    User findByUsername(String username);

    /**
     * 验证用户密码
     * @param user 用户信息
     * @param password 密码
     * @return 是否验证通过
     */
    boolean verifyPassword(User user, String password);

    /**
     * 更新用户最后登录时间
     * @param userId 用户ID
     */
    void updateLastLoginTime(Long userId);
}
