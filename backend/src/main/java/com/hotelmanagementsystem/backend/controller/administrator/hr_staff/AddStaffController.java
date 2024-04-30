package com.hotelmanagementsystem.backend.controller.administrator.hr_staff;

import com.hotelmanagementsystem.backend.service.inter.administrator.hr_staff.AddStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AddStaffController {
    @Autowired
    private AddStaffService addStaffService;

    @PostMapping("/hr_staff/add_staff/")  //要修改数据库，所以用PostMapping
    public Map<String, String> addStaff(@RequestParam Map<String, String> data) {
        return addStaffService.addStaff(data);
    }
}
