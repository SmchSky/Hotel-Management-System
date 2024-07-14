package com.hotelmanagementsystem.backend.controller.administrator.hr_staff;

import com.hotelmanagementsystem.backend.service.inter.administrator.hr_staff.QueryStaffService;
import com.hotelmanagementsystem.backend.utils.user_details.BaseUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/hotel")
public class QueryStaffController {
    
    private final QueryStaffService queryStaffService;
    
    @Autowired
    public QueryStaffController(QueryStaffService queryStaffService) {
        this.queryStaffService = queryStaffService;
    }

    @GetMapping("/hr_staff/query_staff")
    public Map<String, Object> queryStaff(@RequestParam Map<String, String> data) {
        // 身份验证
        String role = ((BaseUserDetails<?>) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getRole();
        if (!role.equals("人事管理员") && !role.equals("财务管理员")) {
            return Map.of("message", "无权限执行该操作！");
        }
        return queryStaffService.queryStaff(data);
    }
}
