package com.uav.management.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class DroneDTO {

    private Long id;

    private String model;

    private String serialNumber;

    private String manufacturer;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date purchaseDate;

    private String status;

    private String aiProperties;

    private Date createTime;

    private Date updateTime;

    private String createBy;

    private String updateBy;
}
