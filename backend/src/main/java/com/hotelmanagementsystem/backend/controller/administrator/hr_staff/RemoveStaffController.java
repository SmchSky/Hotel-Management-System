package com.hotelmanagementsystem.backend.controller.administrator.hr_staff;

import com.hotelmanagementsystem.backend.service.inter.administrator.hr_staff.AddStaffService;
import com.hotelmanagementsystem.backend.service.inter.administrator.hr_staff.RemoveStaffService;
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
public class RemoveStaffController {
    
    private final RemoveStaffService removeStaffService;
    
    @Autowired
    public RemoveStaffController(RemoveStaffService removeStaffService) {
        this.removeStaffService = removeStaffService;
    }

    @PostMapping("/hr_staff/remove_staff")
    public Map<String, String> removeStaff(@RequestParam Map<String, String> data) {
        // 身份验证
        String role = ((BaseUserDetails<?>) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getRole();
        if (!role.equals("人事管理员")) {
            return Map.of("message", "无权限执行该操作！");
        }
        return removeStaffService.removeStaff(data);
    }
}
