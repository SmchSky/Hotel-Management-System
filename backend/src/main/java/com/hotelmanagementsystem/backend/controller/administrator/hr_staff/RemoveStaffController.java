package com.hotelmanagementsystem.backend.controller.administrator.hr_staff;

import com.hotelmanagementsystem.backend.service.inter.administrator.hr_staff.AddStaffService;
import com.hotelmanagementsystem.backend.service.inter.administrator.hr_staff.RemoveStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RemoveStaffController {
    @Autowired
    private RemoveStaffService removeStaffService;

    @PostMapping("/hr_staff/remove_staff/")  //要修改数据库，所以用PostMapping
    public Map<String, String> removeStaff(@RequestParam Map<String, String> data) {
        return removeStaffService.removeStaff(data);
    }
}
