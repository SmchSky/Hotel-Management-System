package com.hotelmanagementsystem.backend.service.inter.administrator.restaurant_staff;

import com.hotelmanagementsystem.backend.pojo.Dish;

import java.util.List;

public interface GetMenuListService {
    List<Dish> getMenu();
}
