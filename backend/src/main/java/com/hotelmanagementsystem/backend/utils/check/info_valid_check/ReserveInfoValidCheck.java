package com.hotelmanagementsystem.backend.utils.check.info_valid_check;

import java.time.LocalDate;

public class ReserveInfoValidCheck {
    /**
     * 预约信息合法性检查
     * @param resident_name 住客姓名
     * @param resident_phone 住客手机号
     * @param checkin_time 预计入住日期
     * @param latest_leave_time 最晚离开日期
     * @return 合法返回success，否则返回错误信息
     */
    public static String checkReserveInfoValid(String resident_name, String resident_phone, String checkin_time, String latest_leave_time) {
        if (resident_name.isEmpty()) {
            return "住客姓名不可为空！";
        }
        if (resident_name.contains(" ")) {
            return "住客姓名不可以包含空格！";
        }
        if (resident_name.length() >= 20) {
            return "住客姓名过长！";
        }
        if (resident_phone.isEmpty()){
            return "手机号不可为空！";
        }
        if (resident_phone.length() != 11) {
            return "手机号格式错误！";
        }
        if (checkin_time == null) {
            return "入住日期不可为空！";
        }
        if (latest_leave_time == null) {
            return "预计离开日期不可为空！";
        }
        LocalDate checkin_date = LocalDate.parse(checkin_time);
        LocalDate latest_leave_date = LocalDate.parse(latest_leave_time);
        if (checkin_date.isAfter(latest_leave_date)) {
            return "预计离开日期要晚于入住日期！";
        }
        return "success";
    }
}
