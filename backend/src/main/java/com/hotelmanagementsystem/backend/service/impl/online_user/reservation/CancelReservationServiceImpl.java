package com.hotelmanagementsystem.backend.service.impl.online_user.reservation;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hotelmanagementsystem.backend.mapper.ReservationRecordMapper;
import com.hotelmanagementsystem.backend.pojo.ReservationRecord;
import com.hotelmanagementsystem.backend.service.inter.online_user.reservation.CancelReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CancelReservationServiceImpl implements CancelReservationService {
    
    private final ReservationRecordMapper reservationRecordMapper;
    
    @Autowired
    public CancelReservationServiceImpl(ReservationRecordMapper reservationRecordMapper) {
        this.reservationRecordMapper = reservationRecordMapper;
    }

    @Override
    public Map<String, String> cancel(Map<String, String> data) {
        Map<String, String> map = new HashMap<>();
        String number = data.get("number");
        UpdateWrapper<ReservationRecord> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("number", number).set("status", "已取消");
        reservationRecordMapper.update(null, updateWrapper);
        map.put("message", "success");
        return map;
    }
}
