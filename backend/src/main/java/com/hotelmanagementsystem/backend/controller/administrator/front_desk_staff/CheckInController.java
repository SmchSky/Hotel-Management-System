package com.hotelmanagementsystem.backend.controller.administrator.front_desk_staff;

import com.hotelmanagementsystem.backend.service.inter.administrator.front_desk_staff.CheckInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CheckInController {
    @Autowired
    private CheckInService checkInService;

    //由于不需要修改数据库，故使用GetMapping
    @PostMapping("/front_desk_staff/check_in/")
    public Map<String, String> checkIn(@RequestParam Map<String, String> data) {
        return checkInService.checkIn(data);
    }
}
