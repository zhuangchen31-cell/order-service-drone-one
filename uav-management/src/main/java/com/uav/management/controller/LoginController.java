package com.uav.management.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    /**
     * 处理根路径请求
     */
    @GetMapping("/")
    public String index() {
        // 检查用户是否已登录
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            // 已登录，跳转到无人机列表页面
            return "redirect:/drone/list";
        } else {
            // 未登录，跳转到登录页面
            return "redirect:/login";
        }
    }

    /**
     * 跳转到登录页面
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 处理登录请求
     */
    @PostMapping("/login")
    public String doLogin(@RequestParam String username, @RequestParam String password, Model model) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        try {
            subject.login(token);
            return "redirect:/drone/list";
        } catch (Exception e) {
            model.addAttribute("error", "用户名或密码错误");
            return "login";
        }
    }

    /**
     * 处理登出请求
     */
    @GetMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/login";
    }

    /**
     * 未授权页面
     */
    @GetMapping("/unauthorized")
    public String unauthorized() {
        return "unauthorized";
    }
}
