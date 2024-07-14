package com.hotelmanagementsystem.backend.controller.administrator.superuser;

import com.hotelmanagementsystem.backend.service.inter.administrator.superuser.AddStaffUserService;
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
public class AddStaffUserController {
    
    private final AddStaffUserService addService;
    
    @Autowired
    public AddStaffUserController(AddStaffUserService addService) {
        this.addService = addService;
    }

    @PostMapping("/superuser/add_staff_user")
    public Map<String, String> addStaffUser(@RequestParam Map<String, String> data) {
        // 身份验证
        String role = ((BaseUserDetails<?>) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getRole();
        if (!role.equals("超级用户")) {
            return Map.of("message", "无权限执行该操作！");
        }
        return addService.addStaffUser(data);
    }
}
