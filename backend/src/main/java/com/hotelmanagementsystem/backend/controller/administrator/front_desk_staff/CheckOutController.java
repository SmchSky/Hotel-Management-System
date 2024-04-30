package com.hotelmanagementsystem.backend.controller.administrator.front_desk_staff;

import com.hotelmanagementsystem.backend.service.inter.administrator.front_desk_staff.CheckOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CheckOutController {
    @Autowired
    private CheckOutService checkOutService;
    @PostMapping("/front_desk_staff/check_out/")
    public Map<String, String> checkOut(@RequestParam Map<String, String> data) {
        return checkOutService.checkOut(data);
    }
}
