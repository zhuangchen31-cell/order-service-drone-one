package com.uav.management.mapper;

import com.uav.management.dto.DroneQueryDTO;
import com.uav.management.entity.Drone;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DroneMapper {

    /**
     * 插入无人机记录
     * @param drone 无人机信息
     * @return 影响行数
     */
    int insert(Drone drone);

    /**
     * 根据ID查询
     * @param id 无人机ID
     * @return 无人机信息
     */
    Drone selectById(Long id);

    /**
     * 条件查询列表
     * @param queryDTO 查询条件
     * @return 无人机列表
     */
    List<Drone> selectByCondition(DroneQueryDTO queryDTO);

    /**
     * 查询总数
     * @param queryDTO 查询条件
     * @return 记录总数
     */
    int countByCondition(DroneQueryDTO queryDTO);

    /**
     * 更新无人机信息
     * @param drone 无人机信息
     * @return 影响行数
     */
    int update(Drone drone);

    /**
     * 物理删除
     * @param id 无人机ID
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 软删除（更新删除标记）
     * @param id 无人机ID
     * @return 影响行数
     */
    int softDeleteById(Long id);

    /**
     * 根据序列号查询无人机
     * @param serialNumber 序列号
     * @return 无人机信息
     */
    Drone selectBySerialNumber(String serialNumber);
}
