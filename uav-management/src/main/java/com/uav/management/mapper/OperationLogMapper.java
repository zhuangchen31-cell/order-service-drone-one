package com.uav.management.mapper;

import com.uav.management.entity.OperationLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OperationLogMapper {

    /**
     * 插入操作日志
     * @param log 操作日志
     * @return 影响行数
     */
    int insert(OperationLog log);
}
