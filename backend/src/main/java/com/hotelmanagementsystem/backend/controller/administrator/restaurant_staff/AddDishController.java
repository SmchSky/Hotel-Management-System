package com.hotelmanagementsystem.backend.controller.administrator.restaurant_staff;

import com.hotelmanagementsystem.backend.service.inter.administrator.restaurant_staff.AddDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AddDishController {
    @Autowired
    private AddDishService addDishService;

    @PostMapping("/restaurant_staff/add_dish/")  //要修改数据库，所以用PostMapping
    public Map<String, String> addRoom(@RequestParam Map<String, String> data) {
        return addDishService.addDish(data);
    }
}
