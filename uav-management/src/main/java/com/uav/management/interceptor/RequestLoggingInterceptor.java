package com.uav.management.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class RequestLoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingInterceptor.class);

    private ThreadLocal<Long> startTimeThreadLocal = new ThreadLocal<>();

    /**
     * 请求前处理
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 记录请求开始时间
        startTimeThreadLocal.set(System.currentTimeMillis());

        // 记录请求信息
        logger.info("Request received: {} {}", request.getMethod(), request.getRequestURI());
        logger.info("Client IP: {}", request.getRemoteAddr());
        logger.info("User-Agent: {}", request.getHeader("User-Agent"));

        return true;
    }

    /**
     * 请求后处理
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        // 可以在这里添加一些处理逻辑
    }

    /**
     * 请求完成后处理
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 计算处理时间
        long startTime = startTimeThreadLocal.get();
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        // 记录响应信息
        logger.info("Request completed: {} {} - Status: {} - Duration: {}ms",
                request.getMethod(), request.getRequestURI(), response.getStatus(), duration);

        // 记录异常信息
        if (ex != null) {
            logger.error("Error processing request: {}", ex.getMessage(), ex);
        }

        // 清理线程本地变量
        startTimeThreadLocal.remove();
    }
}
