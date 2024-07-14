package com.hotelmanagementsystem.backend.utils.check.info_valid_check;

import java.time.LocalDate;

public class QueryRoomInfoValidCheck {
    /**
     * 查询房间信息合法性检查
     * @param checkinTime 预计入住日期
     * @param latestLeaveTime 最晚离开日期
     * @return 合法返回success，否则返回错误信息
     */
    public static String checkQueryRoomInfoValid(String checkinTime, String latestLeaveTime) {
        if (checkinTime.isEmpty()) {
            return "入住日期不可为空！";
        }
        if (latestLeaveTime.isEmpty()) {
            return "预计离开日期不可为空！";
        }
        LocalDate checkin_date = LocalDate.parse(checkinTime);
        LocalDate latest_leave_date = LocalDate.parse(latestLeaveTime);
        if (checkin_date.isAfter(latest_leave_date)) {
            return "预计离开日期要晚于入住日期！";
        }
        return "success";
    }
}
