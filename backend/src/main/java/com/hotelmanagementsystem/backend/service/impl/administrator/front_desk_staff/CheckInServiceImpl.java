package com.hotelmanagementsystem.backend.service.impl.administrator.front_desk_staff;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hotelmanagementsystem.backend.mapper.LiveOrderRecordMapper;
import com.hotelmanagementsystem.backend.mapper.ReservationRecordMapper;
import com.hotelmanagementsystem.backend.pojo.LiveOrderRecord;
import com.hotelmanagementsystem.backend.pojo.ReservationRecord;
import com.hotelmanagementsystem.backend.service.inter.administrator.front_desk_staff.CheckInService;
import com.hotelmanagementsystem.backend.utils.check.info_valid_check.CheckInInfoValidCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatterBuilder;
import java.util.HashMap;
import java.util.Map;

@Service
public class CheckInServiceImpl implements CheckInService {
    
    private final LiveOrderRecordMapper liveOrderRecordMapper;
    private final ReservationRecordMapper reservationRecordMapper;
    
    @Autowired
    public CheckInServiceImpl(LiveOrderRecordMapper liveOrderRecordMapper, ReservationRecordMapper reservationRecordMapper) {
        this.liveOrderRecordMapper = liveOrderRecordMapper;
        this.reservationRecordMapper = reservationRecordMapper;
    }
    
    @Override
    public Map<String, String> checkIn(Map<String, String> data) {
        Map<String, String> map = new HashMap<>();
        String reserveNumber = data.get("number");
        String residentName = data.get("name");
        String residentPhone = data.get("phone");
        String idNumber = data.get("id");
        String totalPrice = data.get("total_price");
        String checkinTime = data.get("checkin_time");
        String latestLeaveTime = data.get("latest_leave_time");
        String roomNumber = data.get("room_number");
        String roomType = data.get("room_type");
        
        // 如果是直接入住
        String message;
        if (reserveNumber == null) {
            // 合法性检验
            message = CheckInInfoValidCheck.checkDirectCheckInfoValid(residentName, residentPhone, idNumber, checkinTime, latestLeaveTime);
            if (!message.equals("success")) {
                map.put("message", message);
                return map;
            }
            // 当前时间
            LocalDateTime now = LocalDateTime.now();
            // 住宿订单编号格式为当前时间字符串+尾缀01
            String orderNumber = now.format(new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd " + "HH:mm:ss").toFormatter()).replaceAll("[-: ]", "").concat("01");
            // 新建对象并插入表中
            LiveOrderRecord liveOrderRecord = new LiveOrderRecord(orderNumber, now, Integer.parseInt(totalPrice), null, residentName, idNumber, residentPhone, roomNumber, roomType, LocalDate.parse(checkinTime), LocalDate.parse(latestLeaveTime), null, "已入住");
            liveOrderRecordMapper.insert(liveOrderRecord);
        }else{
            // 如果是预约入住，此时只有reserveNumber和idNumber不为空，故只需对idNumber进行合法性检验即可
            message = CheckInInfoValidCheck.checkReserveCheckInfoValid(idNumber);
            if (!message.equals("success")) {
                map.put("message", message);
                return map;
            }
            // 从预约表中取出对应的元组
            QueryWrapper<ReservationRecord> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("number", reserveNumber);
            ReservationRecord reservationRecord = reservationRecordMapper.selectOne(queryWrapper);
            
            // 当前时间
            LocalDateTime now = LocalDateTime.now();
            // 住宿订单编号格式为当前时间字符串+尾缀01
            String orderNumber = now.format(new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd " + "HH:mm:ss").toFormatter()).replaceAll("[-: ]", "").concat("01");
            
            // 向住宿订单表中插入新的记录
            LiveOrderRecord liveOrderRecord = new LiveOrderRecord(orderNumber, now, reservationRecord.getPrice(),
                    reserveNumber, reservationRecord.getResidentName(), idNumber, reservationRecord.getResidentPhone(), reservationRecord.getRoomNumber(), reservationRecord.getRoomType(), reservationRecord.getCheckinDate(), reservationRecord.getLatestLeaveDate(), null, "已入住");
            liveOrderRecordMapper.insert(liveOrderRecord);
            
            // 将该预约记录的状态改为“已完成”
            UpdateWrapper<ReservationRecord> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("number", reserveNumber).set("status", "已完成");
            reservationRecordMapper.update(null, updateWrapper);
        }
        map.put("message", "success");
        return map;
    }
}