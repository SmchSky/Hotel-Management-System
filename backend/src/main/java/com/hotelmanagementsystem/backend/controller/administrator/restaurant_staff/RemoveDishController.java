package com.hotelmanagementsystem.backend.controller.administrator.restaurant_staff;

import com.hotelmanagementsystem.backend.service.inter.administrator.restaurant_staff.RemoveDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RemoveDishController {
    @Autowired
    private RemoveDishService removeDishService;
    @PostMapping("/restaurant_staff/remove_dish/")  //要修改数据库，所以用PostMapping
    public Map<String, String> removeDish(@RequestParam Map<String, String> data) {
        return removeDishService.removeDish(data);
    }
}
