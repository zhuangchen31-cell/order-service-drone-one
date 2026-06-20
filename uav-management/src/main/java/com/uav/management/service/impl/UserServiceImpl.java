package com.uav.management.service.impl;

import com.uav.management.entity.User;
import com.uav.management.mapper.UserMapper;
import com.uav.management.service.UserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据用户名查询用户
     */
    @Override
    public User findByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    /**
     * 验证用户密码
     */
    @Override
    public boolean verifyPassword(User user, String password) {
        // 使用MD5加密验证密码
        String encryptedPassword = new Md5Hash(password, user.getSalt()).toHex();
        return encryptedPassword.equals(user.getPassword());
    }

    /**
     * 更新用户最后登录时间
     */
    @Override
    public void updateLastLoginTime(Long userId) {
        User user = new User();
        user.setId(userId);
        userMapper.updateLastLoginTime(user);
    }
}
