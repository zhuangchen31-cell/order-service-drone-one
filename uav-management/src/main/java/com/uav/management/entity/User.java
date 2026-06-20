package com.uav.management.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {

    private Long id;

    private String username;

    private String password;

    private String salt;

    private String role;

    private String status;

    private Date createTime;

    private Date lastLoginTime;
}
