package com.uav.management.service;

import com.uav.management.dto.DroneQueryDTO;
import com.uav.management.dto.PageResult;
import com.uav.management.entity.Drone;
import com.uav.management.exception.BusinessException;
import com.uav.management.exception.DataNotFoundException;
import com.uav.management.exception.ValidationException;

public interface DroneService {

    /**
     * 创建新无人机
     * @param drone 无人机信息
     * @return 创建成功的无人机ID
     * @throws ValidationException 数据验证失败
     * @throws BusinessException 业务逻辑错误
     */
    Long createDrone(Drone drone);

    /**
     * 根据ID查询无人机
     * @param id 无人机ID
     * @return 无人机信息，不存在返回null
     */
    Drone getDroneById(Long id);

    /**
     * 分页查询无人机列表
     * @param queryDTO 查询条件
     * @return 分页结果
     */
    PageResult<Drone> queryDrones(DroneQueryDTO queryDTO);

    /**
     * 更新无人机信息
     * @param drone 无人机信息
     * @return 是否更新成功
     * @throws DataNotFoundException 无人机不存在
     * @throws ValidationException 数据验证失败
     */
    boolean updateDrone(Drone drone);

    /**
     * 删除无人机
     * @param id 无人机ID
     * @param softDelete 是否软删除
     * @return 是否删除成功
     * @throws DataNotFoundException 无人机不存在
     */
    boolean deleteDrone(Long id, boolean softDelete);

    /**
     * 生成AI属性
     * @param droneId 无人机ID
     * @return 生成的AI属性JSON字符串
     */
    String generateAIProperties(Long droneId);
}
