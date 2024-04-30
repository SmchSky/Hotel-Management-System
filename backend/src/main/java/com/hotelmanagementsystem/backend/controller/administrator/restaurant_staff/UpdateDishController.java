package com.hotelmanagementsystem.backend.controller.administrator.restaurant_staff;

import com.hotelmanagementsystem.backend.service.inter.administrator.restaurant_staff.UpdateDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UpdateDishController {
    @Autowired
    private UpdateDishService updateDishService;

    @PostMapping("/restaurant_staff/update_dish/")
    public Map<String, String> updateRoom(@RequestParam Map<String, String> data) {
        return updateDishService.updateDish(data);
    }
}
