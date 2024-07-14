package com.hotelmanagementsystem.backend.utils.check.info_valid_check;

public class StaffInfoValidCheck {
    /**
     * 员工信息合法性检查
     * @param name 员工姓名
     * @param id 身份证号
     * @param phone 手机号
     * @param duty 职务
     * @param basic_salary 基本工资
     * @param nation 民族
     * @param birthday 生日
     * @param native_place 籍贯
     * @param entry_date 入职日期
     * @param residential_address 家庭住址
     * @return 合法返回success，否则返回错误信息
     */
    public static String checkStaffInfoValid(String name, String id, String phone, String duty, String basic_salary, String nation, String birthday, String native_place, String entry_date, String residential_address) {
        if (name.isEmpty()) {
            return "姓名不能为空！";
        }
        if (id.length() != 18) {
            return "身份证号格式错误！";
        }
        if (phone.length() != 11) {
            return "手机号格式错误！";
        }
        if (duty.isEmpty()) {
            return "职务不能为空！";
        }
        if (basic_salary.isEmpty()) {
            return "基本工资不能为空！";
        }
        if (nation.isEmpty()) {
            return "民族不能为空！";
        }
        if (birthday.isEmpty()) {
            return "出生日期不能为空！";
        }
        if (native_place.isEmpty()) {
            return "籍贯不能为空！";
        }
        if (entry_date.isEmpty()) {
            return "入职时间不能为空！";
        }
        if (residential_address.isEmpty()) {
            return "家庭住址不能为空！";
        }
        return "success";
    }
}
