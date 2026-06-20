package com.uav.management.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class DroneQueryDTO {

    private String model;

    private String serialNumber;

    private String status;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date purchaseDateStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date purchaseDateEnd;

    // 分页参数
    private Integer pageNum = 1;

    private Integer pageSize = 10;

    // 排序参数
    private String orderBy = "create_time";

    private String orderDirection = "DESC";

    // 计算偏移量
    public int getOffset() {
        int page = this.pageNum != null ? this.pageNum : 1;
        int size = this.pageSize != null ? this.pageSize : 10;
        return (page - 1) * size;
    }
}
