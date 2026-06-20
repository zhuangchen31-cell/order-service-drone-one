package com.uav.management.controller;

import com.uav.management.exception.BusinessException;
import com.uav.management.exception.DataNotFoundException;
import com.uav.management.exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ErrorController {

    private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);

    /**
     * 处理业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public ModelAndView handleBusinessException(BusinessException e) {
        logger.error("Business exception: {}", e.getMessage(), e);
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("error", e.getMessage());
        return modelAndView;
    }

    /**
     * 处理验证异常
     */
    @ExceptionHandler(ValidationException.class)
    public ModelAndView handleValidationException(ValidationException e) {
        logger.warn("Validation exception: {}", e.getMessage());
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("error", e.getMessage());
        return modelAndView;
    }

    /**
     * 处理数据未找到异常
     */
    @ExceptionHandler(DataNotFoundException.class)
    public ModelAndView handleDataNotFoundException(DataNotFoundException e) {
        logger.warn("Data not found: {}", e.getMessage());
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("error", e.getMessage());
        return modelAndView;
    }

    /**
     * 处理其他异常
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception e) {
        logger.error("System exception: {} - {}", e.getClass().getName(), e.getMessage(), e);
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("error", "系统错误：" + e.getClass().getName() + " - " + e.getMessage());
        return modelAndView;
    }
}
