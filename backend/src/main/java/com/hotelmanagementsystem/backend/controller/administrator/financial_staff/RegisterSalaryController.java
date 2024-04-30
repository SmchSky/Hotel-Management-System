package com.hotelmanagementsystem.backend.controller.administrator.financial_staff;

import com.hotelmanagementsystem.backend.service.inter.administrator.financial_staff.RegisterSalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RegisterSalaryController {
    @Autowired
    private RegisterSalaryService registerSalaryService;

    @PostMapping("/financial_staff/register_salary/")  //要修改数据库，所以用PostMapping
    public Map<String ,String> registerSalary(@RequestParam Map<String, String> data) {
        return registerSalaryService.registerSalary(data);
    }
}
