package com.hotelmanagementsystem.backend.utils.check.info_valid_check;

public class DishInfoValidCheck {
    public static String checkDishInfoValid(String name, String price, String status) {
        if (name.isEmpty()) {
            return "菜品名称不可为空！";
        }
        if (name.contains(" ")) {
            return "菜品名称不可包含空格！";
        }
        if (price.isEmpty()) {
            return "菜品价格不可为空！";
        }
        return "success";
    }
}
