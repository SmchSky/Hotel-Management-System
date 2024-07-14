package com.hotelmanagementsystem.backend.utils.check.info_valid_check;

public class CheckInInfoValidCheck {
    /**
     * 直接入住信息合法性检查
     *
     * @param resident_name     住客姓名
     * @param resident_phone    住客手机号
     * @param idNumber          住客身份证号
     * @param checkin_time      预计入住日期
     * @param latest_leave_time 最晚离开日期
     * @return 合法返回success，否则返回错误信息
     */
    public static String checkDirectCheckInfoValid(String resident_name, String resident_phone, String idNumber, String checkin_time, String latest_leave_time) {
        String message = ReserveInfoValidCheck.checkReserveInfoValid(resident_name, resident_phone, checkin_time, latest_leave_time);
        if (!message.equals("success")) {
            return message;
        }
        message = checkIdNumber(idNumber);
        return message;
    }
    
    /**
     * 预约入住信息合法性检查
     *
     * @param idNumber 住客身份证号
     * @return 合法返回success，否则返回错误信息
     */
    public static String checkReserveCheckInfoValid(String idNumber) {
        return checkIdNumber(idNumber);
    }
    
    /**
     * 检查身份证号合法性
     *
     * @param idNumber 身份证号
     * @return 合法返回success，否则返回错误信息
     */
    private static String checkIdNumber(String idNumber) {
        if (idNumber.isEmpty()) {
            return "住客身份证号不能为空！";
        }
        if (idNumber.length() != 18) {
            return "住客身份证号格式错误！";
        }
        return "success";
    }
}
