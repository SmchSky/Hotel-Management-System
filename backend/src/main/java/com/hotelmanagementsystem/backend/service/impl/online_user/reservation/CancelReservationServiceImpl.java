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
    @Autowired
    private ReservationRecordMapper reservationRecordMapper;

    @Override
    public Map<String, String> cancel(Map<String, String> data) {
        //返回的map
        Map<String, String> map = new HashMap<>();
        //取出信息
        String number = data.get("number");
        //查询并更新预约记录的状态为“已取消”
        UpdateWrapper<ReservationRecord> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("number", number).set("status", "已取消");
        reservationRecordMapper.update(null, updateWrapper);
        //返回map
        map.put("error_message", "success");
        return map;
    }
}
