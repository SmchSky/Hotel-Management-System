package com.hotelmanagementsystem.backend.utils.check.info_valid_check;

public class RoomInfoValidCheck {
    public static String checkRoomInfoValid(String room_number, String room_type, String area, String price) {
        if (room_number.isEmpty()) {
            return "房间编号不能为空！";
        }
        if (room_number.length() != 10) {
            return "房间编号长度必须为10！";
        }
        if (area.isEmpty()) {
            return "房间面积不能为空！";
        }
        if (price.isEmpty()) {
            return "房间价格不能为空！";
        }
        return "success";
    }
}
