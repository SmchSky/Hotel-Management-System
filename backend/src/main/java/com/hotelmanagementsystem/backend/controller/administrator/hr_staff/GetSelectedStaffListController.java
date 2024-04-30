package com.hotelmanagementsystem.backend.controller.administrator.hr_staff;

import com.hotelmanagementsystem.backend.service.inter.administrator.hr_staff.GetSelectedStaffListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class GetSelectedStaffListController {
    @Autowired
    private GetSelectedStaffListService getSelectedStaffListService;

    @GetMapping("/hr_staff/get_selected_staff_list/")  //要修改数据库，所以用PostMapping
    public Map<String, Object> getList(@RequestParam Map<String, String> data) {
        return getSelectedStaffListService.getList(data);
    }
}
