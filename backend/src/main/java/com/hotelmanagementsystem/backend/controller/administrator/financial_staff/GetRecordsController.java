package com.hotelmanagementsystem.backend.controller.administrator.financial_staff;

import com.hotelmanagementsystem.backend.service.inter.administrator.financial_staff.GetRecordsService;
import com.hotelmanagementsystem.backend.utils.user_details.BaseUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/hotel")
public class GetRecordsController {
    private final GetRecordsService getRecordsService;
    
    @Autowired
    public GetRecordsController(GetRecordsService getRecordsService) {
        this.getRecordsService = getRecordsService;
    }

    @GetMapping("/financial_staff/get_records")
    public Map<String,Object> getRecords(@RequestParam Map<String, String> data) {
        // 身份验证
        String role = ((BaseUserDetails<?>) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getRole();
        if (!role.equals("财务管理员")) {
            return Map.of("message", "无权限执行该操作！");
        }
        return getRecordsService.getRecords(data);
    }
}
