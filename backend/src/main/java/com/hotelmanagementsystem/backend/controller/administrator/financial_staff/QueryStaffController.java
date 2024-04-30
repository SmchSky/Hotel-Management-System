package com.hotelmanagementsystem.backend.controller.administrator.financial_staff;

import com.hotelmanagementsystem.backend.service.inter.administrator.financial_staff.QueryStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class QueryStaffController {
    @Autowired
    private QueryStaffService queryStaffService;

    @GetMapping("/financial_staff/query_staff/")
    public Map<String,Object> queryStaff(@RequestParam Map<String, String> data) {
        return queryStaffService.queryStaff(data);
    }
}
