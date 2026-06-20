package com.uav.management.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OperationLog implements Serializable {

    private Long id;

    private Long userId;

    private String username;

    private String operation;

    private String method;

    private String params;

    private String ip;

    private Date createTime;

    private Integer duration;

    private String status;

    private String errorMsg;
}
