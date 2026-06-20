package com.uav.management.service;

import com.uav.management.entity.Drone;

import java.util.concurrent.CompletableFuture;

public interface AIPropertyService {

    /**
     * 同步生成AI属性
     * @param drone 无人机基本信息
     * @return AI属性JSON字符串
     */
    String generate(Drone drone);

    /**
     * 异步生成AI属性
     * @param droneId 无人机ID
     * @return Future对象
     */
    CompletableFuture<String> generateAsync(Long droneId);
}
