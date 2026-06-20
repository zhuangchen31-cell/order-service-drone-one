package com.uav.management.controller;

import com.uav.management.dto.DroneQueryDTO;
import com.uav.management.dto.PageResult;
import com.uav.management.entity.Drone;
import com.uav.management.exception.DataNotFoundException;
import com.uav.management.exception.ValidationException;
import com.uav.management.service.DroneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/drone")
public class DroneController {

    private static final Logger logger = LoggerFactory.getLogger(DroneController.class);

    @Autowired
    private DroneService droneService;

    /**
     * 跳转到无人机列表页面
     */
    @GetMapping("/list")
    public String list(DroneQueryDTO queryDTO, Model model) {
        try {
            // 确保查询参数不为空
            if (queryDTO == null) {
                queryDTO = new DroneQueryDTO();
            }
            
            PageResult<Drone> pageResult = droneService.queryDrones(queryDTO);
            model.addAttribute("pageResult", pageResult);
            model.addAttribute("queryDTO", queryDTO);
            
            logger.info("查询无人机列表成功，共 {} 条记录", 
                pageResult != null && pageResult.getRecords() != null ? pageResult.getRecords().size() : 0);
            
            return "drone/list";
        } catch (Exception e) {
            logger.error("查询无人机列表失败", e);
            model.addAttribute("error", "查询无人机列表失败：" + e.getMessage());
            // 即使出错也返回列表页，显示空数据
            model.addAttribute("pageResult", new PageResult<>(null, 0L, 1, 10));
            model.addAttribute("queryDTO", queryDTO != null ? queryDTO : new DroneQueryDTO());
            return "drone/list";
        }
    }

    /**
     * 跳转到添加无人机页面
     */
    @GetMapping("/add")
    public String add() {
        return "drone/add";
    }

    /**
     * 处理添加无人机请求
     */
    @PostMapping("/add")
    public String doAdd(Drone drone, Model model) {
        try {
            logger.info("接收到添加无人机请求: {}", drone.getSerialNumber());
            Long id = droneService.createDrone(drone);
            logger.info("无人机添加成功，ID: {}", id);
            return "redirect:/drone/list?action=add";
        } catch (ValidationException e) {
            logger.warn("无人机验证失败: {}", e.getMessage());
            model.addAttribute("error", e.getMessage());
            model.addAttribute("drone", drone);
            return "drone/add";
        } catch (Exception e) {
            logger.error("无人机添加失败", e);
            model.addAttribute("error", "添加失败：" + e.getMessage());
            model.addAttribute("drone", drone);
            return "drone/add";
        }
    }

    /**
     * 跳转到编辑无人机页面
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        logger.info("编辑无人机，ID: {}", id);
        
        Drone drone = droneService.getDroneById(id);
        if (drone == null) {
            logger.warn("无人机不存在，ID: {}", id);
            model.addAttribute("error", "无人机不存在");
            return "redirect:/drone/list";
        }
        
        model.addAttribute("drone", drone);
        return "drone/edit";
    }

    /**
     * 处理编辑无人机请求
     */
    @PostMapping("/edit")
    public String doEdit(Drone drone, Model model) {
        try {
            logger.info("更新无人机信息，ID: {}", drone.getId());
            droneService.updateDrone(drone);
            logger.info("无人机更新成功，ID: {}", drone.getId());
            return "redirect:/drone/list";
        } catch (DataNotFoundException e) {
            logger.warn("无人机不存在: {}", e.getMessage());
            model.addAttribute("error", e.getMessage());
            model.addAttribute("drone", drone);
            return "drone/edit";
        } catch (ValidationException e) {
            logger.warn("无人机验证失败: {}", e.getMessage());
            model.addAttribute("error", e.getMessage());
            model.addAttribute("drone", drone);
            return "drone/edit";
        } catch (Exception e) {
            logger.error("无人机更新失败", e);
            model.addAttribute("error", "更新失败：" + e.getMessage());
            model.addAttribute("drone", drone);
            return "drone/edit";
        }
    }

    /**
     * 处理删除无人机请求
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        try {
            logger.info("删除无人机，ID: {}", id);
            droneService.deleteDrone(id, true);
            logger.info("无人机删除成功，ID: {}", id);
            return "redirect:/drone/list?action=delete";
        } catch (DataNotFoundException e) {
            logger.warn("删除失败：无人机不存在，ID: {}", id);
            return "redirect:/drone/list";
        } catch (Exception e) {
            logger.error("无人机删除失败，ID: {}", id, e);
            return "redirect:/drone/list";
        }
    }

    /**
     * 处理生成AI属性请求
     */
    @GetMapping("/generate-ai/{id}")
    public String generateAI(@PathVariable Long id) {
        try {
            logger.info("生成AI属性，无人机ID: {}", id);
            droneService.generateAIProperties(id);
            logger.info("AI属性生成成功，无人机ID: {}", id);
            return "redirect:/drone/list";
        } catch (DataNotFoundException e) {
            logger.warn("生成AI属性失败：无人机不存在，ID: {}", id);
            return "redirect:/drone/list";
        } catch (Exception e) {
            logger.error("AI属性生成失败，无人机ID: {}", id, e);
            return "redirect:/drone/list";
        }
    }
}
