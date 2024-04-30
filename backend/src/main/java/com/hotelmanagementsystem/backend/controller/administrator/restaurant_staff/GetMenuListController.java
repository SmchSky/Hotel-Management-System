package com.hotelmanagementsystem.backend.controller.administrator.restaurant_staff;

import com.hotelmanagementsystem.backend.pojo.Dish;
import com.hotelmanagementsystem.backend.service.inter.administrator.restaurant_staff.GetMenuListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetMenuListController {
    @Autowired
    private GetMenuListService getMenuListService;

    //由于不需要修改数据库，故使用GetMapping
    @GetMapping("/restaurant_staff/get_menu/")
    public List<Dish> getMenu() {
        return getMenuListService.getMenu();
    }
}
