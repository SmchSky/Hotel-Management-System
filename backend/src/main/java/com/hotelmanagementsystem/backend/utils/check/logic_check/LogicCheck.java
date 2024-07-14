package com.hotelmanagementsystem.backend.utils.check.logic_check;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hotelmanagementsystem.backend.mapper.LiveOrderRecordMapper;
import com.hotelmanagementsystem.backend.mapper.ReservationRecordMapper;
import com.hotelmanagementsystem.backend.pojo.LiveOrderRecord;
import com.hotelmanagementsystem.backend.pojo.ReservationRecord;

import java.util.List;

public class LogicCheck {
    /**
     * 判断该客房是否正在被使用（如果该客房在住宿预约表中对应的记录的状态处于“等待入住”或者在住宿订单表中对应的记录的状态处于“已入住”，则视为正则被使用）
     *
     * @param room_number             房间编号
     * @param reservationRecordMapper 用于查询预约记录的Mapper
     * @param liveOrderRecordMapper   用于查询住宿订单记录的Mapper
     * @return 正在被使用返回true，否则返回false
     */
    public static boolean isRoomBeingUsed(String room_number, ReservationRecordMapper reservationRecordMapper, LiveOrderRecordMapper liveOrderRecordMapper) {
        QueryWrapper<ReservationRecord> queryWrapper1 = new QueryWrapper<>();
        List<ReservationRecord> list_1 = reservationRecordMapper.selectList(queryWrapper1.eq("room_number", room_number).eq("status", "等待入住"));
        if (!list_1.isEmpty()) {
            return true;
        }
        QueryWrapper<LiveOrderRecord> queryWrapper2 = new QueryWrapper<>();
        List<LiveOrderRecord> list_2 = liveOrderRecordMapper.selectList(queryWrapper2.eq("room_number", room_number).eq("status", "已入住"));
        return !list_2.isEmpty();
    }
}
