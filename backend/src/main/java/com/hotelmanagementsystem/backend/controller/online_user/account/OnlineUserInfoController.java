package com.hotelmanagementsystem.backend.controller.online_user.account;

import com.hotelmanagementsystem.backend.service.inter.online_user.account.OnlineUserInfoService;
import com.hotelmanagementsystem.backend.utils.user_details.BaseUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/hotel")
public class OnlineUserInfoController {
    
    private final OnlineUserInfoService infoService;
    
    @Autowired
    public OnlineUserInfoController(OnlineUserInfoService infoService) {
        this.infoService = infoService;
    }

    @GetMapping("/online_user/info")
    public Map<String, String> getInfo() {
        // 身份验证
        String role = ((BaseUserDetails<?>) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getRole();
        if (!role.equals("线上用户")) {
            return Map.of("message", "无权限执行该操作！");
        }
        // 获取当前已登录的用户信息
        return infoService.getUserInfo();
    }
}
