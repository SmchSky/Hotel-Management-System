package com.hotelmanagementsystem.backend.controller.administrator.superuser;

import com.hotelmanagementsystem.backend.service.inter.administrator.superuser.RemoveStaffUserService;
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
public class RemoveStaffUserController {
    
    private final RemoveStaffUserService removeService;
    
    @Autowired
    public RemoveStaffUserController(RemoveStaffUserService removeService) {
        this.removeService = removeService;
    }

    @PostMapping("/superuser/remove_staff_user")
    public Map<String, String> removeStaffUser(@RequestParam Map<String, String> data) {
        // 身份验证
        String role = ((BaseUserDetails<?>) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getRole();
        if (!role.equals("超级用户")) {
            return Map.of("message", "无权限执行该操作！");
        }
        return removeService.removeStaffUser(data);
    }
}
