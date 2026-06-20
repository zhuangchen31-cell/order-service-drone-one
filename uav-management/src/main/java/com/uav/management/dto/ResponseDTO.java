package com.uav.management.dto;

import lombok.Data;

/**
 * 统一响应DTO
 */
@Data
public class ResponseDTO<T> {

    /**
     * 响应码
     */
    private int code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 响应时间戳
     */
    private long timestamp;

    private ResponseDTO(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * 成功响应
     */
    public static <T> ResponseDTO<T> success(T data) {
        return new ResponseDTO<>(200, "success", data);
    }

    /**
     * 成功响应（带消息）
     */
    public static <T> ResponseDTO<T> success(String message, T data) {
        return new ResponseDTO<>(200, message, data);
    }

    /**
     * 成功响应（无数据）
     */
    public static <T> ResponseDTO<T> success() {
        return new ResponseDTO<>(200, "success", null);
    }

    /**
     * 失败响应
     */
    public static <T> ResponseDTO<T> error(int code, String message) {
        return new ResponseDTO<>(code, message, null);
    }

    /**
     * 失败响应（默认错误码）
     */
    public static <T> ResponseDTO<T> error(String message) {
        return new ResponseDTO<>(500, message, null);
    }

    /**
     * 参数错误响应
     */
    public static <T> ResponseDTO<T> badRequest(String message) {
        return new ResponseDTO<>(400, message, null);
    }

    /**
     * 未授权响应
     */
    public static <T> ResponseDTO<T> unauthorized(String message) {
        return new ResponseDTO<>(401, message, null);
    }

    /**
     * 未找到响应
     */
    public static <T> ResponseDTO<T> notFound(String message) {
        return new ResponseDTO<>(404, message, null);
    }
}