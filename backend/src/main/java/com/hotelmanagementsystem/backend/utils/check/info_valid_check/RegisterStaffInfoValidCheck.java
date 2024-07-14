package com.hotelmanagementsystem.backend.utils.check.info_valid_check;

public class RegisterStaffInfoValidCheck {
    public static String checkRegisterStaffInfoValid(String stuff_name, String quantity, String price, String position, String date_string, String staff_name, String confirmed_staff_name) {
        if (stuff_name.isEmpty()) {
            return "产品名称不能为空！";
        }
        if (quantity.isEmpty()) {
            return "采购数量不能为空！";
        }
        if (position.isEmpty()) {
            return "采购地点不能为空！";
        }
        if (price.isEmpty()) {
            return "采购费用不能为空！";
        }
        if (date_string.isEmpty()) {
            return "采购日期不能为空！";
        }
        if (staff_name.isEmpty()) {
            return "采购人员姓名不能为空！";
        }
        if (confirmed_staff_name.isEmpty()) {
            return "批准采购的人员姓名不能为空！";
        }
        return "success";
    }
}
