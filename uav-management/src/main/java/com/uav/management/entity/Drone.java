package com.uav.management.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Data
public class Drone implements Serializable {

    private Long id;

    @NotBlank(message = "无人机型号不能为空")
    @Size(max = 100, message = "型号长度不能超过100")
    private String model;

    @NotBlank(message = "序列号不能为空")
    @Size(max = 100, message = "序列号长度不能超过100")
    private String serialNumber;

    @NotBlank(message = "制造商不能为空")
    @Size(max = 100, message = "制造商长度不能超过100")
    private String manufacturer;

    @NotNull(message = "购买日期不能为空")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date purchaseDate;

    @NotBlank(message = "状态不能为空")
    private String status;

    private String aiProperties;

    private Integer deleted;

    private Date createTime;

    private Date updateTime;

    private String createBy;

    private String updateBy;
}
