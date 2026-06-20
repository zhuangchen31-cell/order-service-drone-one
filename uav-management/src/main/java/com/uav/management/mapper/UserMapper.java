package com.uav.management.mapper;

import com.uav.management.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户信息
     */
    User selectByUsername(String username);

    /**
     * 根据ID查询用户
     * @param id 用户ID
     * @return 用户信息
     */
    User selectById(Long id);

    /**
     * 更新用户最后登录时间
     * @param user 用户信息
     * @return 影响行数
     */
    int updateLastLoginTime(User user);
}
