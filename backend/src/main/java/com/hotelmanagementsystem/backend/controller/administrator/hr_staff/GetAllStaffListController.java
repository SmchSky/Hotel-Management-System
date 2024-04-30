package com.hotelmanagementsystem.backend.controller.administrator.hr_staff;

import com.hotelmanagementsystem.backend.service.inter.administrator.hr_staff.GetAllStaffListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetAllStaffListController {
    @Autowired
    private GetAllStaffListService getAllStaffListService;

    @GetMapping("/hr_staff/get_all_staff_list/")  //要修改数据库，所以用PostMapping
    public Map<String, Object> getList() {
        return getAllStaffListService.getList();
    }
}
