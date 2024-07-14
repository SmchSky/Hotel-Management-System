package com.hotelmanagementsystem.backend.controller.online_user.account;

import com.hotelmanagementsystem.backend.service.inter.online_user.account.OnlineUserInfoUpdateService;
import com.hotelmanagementsystem.backend.utils.user_details.BaseUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/hotel")
public class OnlineUserInfoUpdateController {
    
    private final OnlineUserInfoUpdateService onlineUserInfoUpdateService;
    
    @Autowired
    public OnlineUserInfoUpdateController(OnlineUserInfoUpdateService onlineUserInfoUpdateService) {
        this.onlineUserInfoUpdateService = onlineUserInfoUpdateService;
    }

    @PostMapping("/online_user/update_info")
    public Map<String, String> update(@RequestParam Map<String, String> data) {
        // 身份验证
        String role = ((BaseUserDetails<?>) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getRole();
        if (!role.equals("线上用户")) {
            return Map.of("message", "无权限执行该操作！");
        }
        return onlineUserInfoUpdateService.updateUserInfo(data);
    }
}
