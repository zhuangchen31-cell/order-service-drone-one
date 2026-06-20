package com.uav.management.constant;

public class SystemConstant {

    // 无人机状态
    public static final String DRONE_STATUS_IN_USE = "IN_USE";
    public static final String DRONE_STATUS_MAINTENANCE = "MAINTENANCE";
    public static final String DRONE_STATUS_RETIRED = "RETIRED";

    // 用户角色
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_USER = "USER";

    // 用户状态
    public static final String USER_STATUS_ACTIVE = "ACTIVE";
    public static final String USER_STATUS_LOCKED = "LOCKED";

    // 操作类型
    public static final String OPERATION_CREATE = "CREATE";
    public static final String OPERATION_UPDATE = "UPDATE";
    public static final String OPERATION_DELETE = "DELETE";
    public static final String OPERATION_QUERY = "QUERY";

    // 操作状态
    public static final String OPERATION_STATUS_SUCCESS = "SUCCESS";
    public static final String OPERATION_STATUS_FAIL = "FAIL";

    // 分页默认值
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int DEFAULT_PAGE_NUM = 1;

    // AI属性生成超时时间（毫秒）
    public static final long AI_GENERATION_TIMEOUT = 5000;
}
