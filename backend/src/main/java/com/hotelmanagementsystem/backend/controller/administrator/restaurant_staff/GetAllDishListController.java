package com.hotelmanagementsystem.backend.controller.administrator.restaurant_staff;

import com.hotelmanagementsystem.backend.pojo.Dish;
import com.hotelmanagementsystem.backend.service.inter.administrator.restaurant_staff.GetAllDishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetAllDishListController {
    @Autowired
    private GetAllDishListService getAllDishListService;

    @GetMapping("/restaurant_staff/get_all_dish_list/")
    public List<Dish> getMenu() {
        return getAllDishListService.getAllDishList();
    }
}
