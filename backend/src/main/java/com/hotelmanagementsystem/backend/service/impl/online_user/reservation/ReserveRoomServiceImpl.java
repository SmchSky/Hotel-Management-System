package com.hotelmanagementsystem.backend.service.impl.online_user.reservation;

import com.hotelmanagementsystem.backend.mapper.ReservationRecordMapper;
import com.hotelmanagementsystem.backend.pojo.ReservationRecord;
import com.hotelmanagementsystem.backend.service.inter.online_user.reservation.ReserveRoomService;
import com.hotelmanagementsystem.backend.utils.check.info_valid_check.ReserveInfoValidCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatterBuilder;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReserveRoomServiceImpl implements ReserveRoomService {
    
    private final ReservationRecordMapper reservationRecordMapper;
    
    @Autowired
    public ReserveRoomServiceImpl(ReservationRecordMapper reservationRecordMapper) {
        this.reservationRecordMapper = reservationRecordMapper;
    }
    
    @Override
    public Map<String, String> reserve(Map<String, String> data) {
        Map<String, String> map = new HashMap<>();
        String residentName = data.get("resident_name");
        String residentPhone = data.get("resident_phone");
        String username = data.get("username");
        String userPhone = data.get("user_phone");
        String totalPrice = data.get("total_price");
        // 此处日期格式为JDBC格式，即yyyy-[m]m-[d]d
        String checkinTime = data.get("checkin_time");
        String latestLeaveTime = data.get("latest_leave_time");
        String roomNumber = data.get("room_number");
        String roomType = data.get("room_type");
        // 合法性检验
        String message = ReserveInfoValidCheck.checkReserveInfoValid(residentName, residentPhone, checkinTime, latestLeaveTime);
        if (!message.equals("success")) {
            map.put("message", message);
            return map;
        }
        // 当前时间
        LocalDateTime now = LocalDateTime.now();
        // 预约编号格式为当前时间字符串+尾缀03
        String reserveNumber = now.format(new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd " + "HH:mm:ss").toFormatter()).replaceAll("[-: ]", "").concat("03");
        // 新建对象并插入表中
        ReservationRecord reservationRecord = new ReservationRecord(reserveNumber, now, username, userPhone, residentName, residentPhone, roomNumber, roomType, Integer.parseInt(totalPrice), LocalDate.parse(checkinTime), LocalDate.parse(latestLeaveTime), "等待入住");
        reservationRecordMapper.insert(reservationRecord);
        map.put("message", "success");
        return map;
    }
}