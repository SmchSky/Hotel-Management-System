package com.hotelmanagementsystem.backend.controller.administrator.account;

import com.hotelmanagementsystem.backend.service.inter.administrator.account.StaffUserInfoUpdateService;
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
public class StaffUserInfoUpdateController {
    
    private final StaffUserInfoUpdateService staffUserInfoUpdateService;

    @Autowired
    public StaffUserInfoUpdateController(StaffUserInfoUpdateService staffUserInfoUpdateService) {
        this.staffUserInfoUpdateService = staffUserInfoUpdateService;
    }
    @PostMapping("/staff_user/update_info")
    public Map<String, String> update(@RequestParam Map<String, String> data) {
        // 身份验证
        String role = ((BaseUserDetails<?>) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getRole();
        if (role.equals("线上用户")) {
            return Map.of("message", "无权限执行该操作！");
        }
        return staffUserInfoUpdateService.update(data);
    }

}
