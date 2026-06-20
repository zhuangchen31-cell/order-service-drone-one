package com.uav.management.service.impl;

import com.uav.management.dto.DroneQueryDTO;
import com.uav.management.dto.PageResult;
import com.uav.management.entity.Drone;
import com.uav.management.exception.DataNotFoundException;
import com.uav.management.exception.ValidationException;
import com.uav.management.mapper.DroneMapper;
import com.uav.management.service.AIPropertyService;
import com.uav.management.service.DroneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class DroneServiceImpl implements DroneService {

    private static final Logger logger = LoggerFactory.getLogger(DroneServiceImpl.class);

    @Autowired
    private DroneMapper droneMapper;

    @Autowired
    private AIPropertyService aiPropertyService;

    /**
     * 创建新无人机
     */
    @Override
    @Transactional
    public Long createDrone(Drone drone) {
        logger.info("开始创建无人机，序列号: {}", drone.getSerialNumber());
        
        // 检查序列号是否存在
        Drone existingDrone = droneMapper.selectBySerialNumber(drone.getSerialNumber());
        if (existingDrone != null) {
            logger.warn("序列号已存在: {}", drone.getSerialNumber());
            throw new ValidationException("序列号已经存在，请使用其他序列号");
        }

        // 设置默认值
        drone.setDeleted(0);
        drone.setCreateTime(new Date());
        drone.setUpdateTime(new Date());
        drone.setCreateBy("admin");
        drone.setUpdateBy("admin");

        // 插入数据库
        int result = droneMapper.insert(drone);
        if (result != 1) {
            logger.error("无人机创建失败，插入返回值: {}", result);
            throw new ValidationException("无人机创建失败");
        }

        logger.info("无人机创建成功，ID: {}", drone.getId());

        // 异步生成AI属性
        CompletableFuture.supplyAsync(() -> {
            try {
                return aiPropertyService.generate(drone);
            } catch (Exception e) {
                logger.warn("AI属性生成失败", e);
                return null;
            }
        }).thenAccept(aiProperties -> {
            if (aiProperties != null) {
                drone.setAiProperties(aiProperties);
                drone.setUpdateTime(new Date());
                droneMapper.update(drone);
                logger.info("AI属性生成并更新成功");
            }
        });

        return drone.getId();
    }

    /**
     * 根据ID查询无人机
     */
    @Override
    public Drone getDroneById(Long id) {
        return droneMapper.selectById(id);
    }

    /**
     * 分页查询无人机列表
     */
    @Override
    public PageResult<Drone> queryDrones(DroneQueryDTO queryDTO) {
        logger.info("开始查询无人机列表，查询条件: {}", queryDTO);
        
        try {
            List<Drone> drones = droneMapper.selectByCondition(queryDTO);
            int count = droneMapper.countByCondition(queryDTO);
            logger.info("查询结果: 共 {} 条记录，当前页 {} 条", count, drones.size());
            return new PageResult<>(drones, (long) count, queryDTO.getPageNum(), queryDTO.getPageSize());
        } catch (Exception e) {
            logger.error("查询无人机列表失败", e);
            // 数据库查询失败，返回空结果
            return new PageResult<>(java.util.Collections.emptyList(), 0L, 
                queryDTO.getPageNum(), queryDTO.getPageSize());
        }
    }

    /**
     * 更新无人机信息
     */
    @Override
    @Transactional
    public boolean updateDrone(Drone drone) {
        // 检查无人机是否存在
        Drone existingDrone = droneMapper.selectById(drone.getId());
        if (existingDrone == null) {
            throw new DataNotFoundException("无人机不存在");
        }

        // 更新时间
        drone.setUpdateTime(new Date());

        // 执行更新
        int result = droneMapper.update(drone);
        return result == 1;
    }

    /**
     * 删除无人机
     */
    @Override
    @Transactional
    public boolean deleteDrone(Long id, boolean softDelete) {
        // 检查无人机是否存在
        Drone existingDrone = droneMapper.selectById(id);
        if (existingDrone == null) {
            throw new DataNotFoundException("无人机不存在");
        }

        // 执行删除
        int result;
        if (softDelete) {
            result = droneMapper.softDeleteById(id);
        } else {
            result = droneMapper.deleteById(id);
        }

        return result == 1;
    }

    /**
     * 生成AI属性
     */
    @Override
    public String generateAIProperties(Long droneId) {
        Drone drone = droneMapper.selectById(droneId);
        if (drone == null) {
            throw new DataNotFoundException("无人机不存在");
        }

        String aiProperties = aiPropertyService.generate(drone);
        drone.setAiProperties(aiProperties);
        drone.setUpdateTime(new Date());
        droneMapper.update(drone);

        return aiProperties;
    }
}
