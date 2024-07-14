package com.hotelmanagementsystem.backend.controller.administrator.financial_staff;

import com.hotelmanagementsystem.backend.service.inter.administrator.financial_staff.RegisterSalaryService;
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
public class RegisterSalaryController {
    
    private final RegisterSalaryService registerSalaryService;
    
    @Autowired
    public RegisterSalaryController(RegisterSalaryService registerSalaryService) {
        this.registerSalaryService = registerSalaryService;
    }

    @PostMapping("/financial_staff/register_salary")
    public Map<String ,String> registerSalary(@RequestParam Map<String, String> data) {
        // 身份验证
        String role = ((BaseUserDetails<?>) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getRole();
        if (!role.equals("财务管理员")) {
            return Map.of("message", "无权限执行该操作！");
        }
        return registerSalaryService.registerSalary(data);
    }
}
