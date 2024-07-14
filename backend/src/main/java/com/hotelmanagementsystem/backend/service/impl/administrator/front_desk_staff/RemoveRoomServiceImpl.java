package com.hotelmanagementsystem.backend.service.impl.administrator.front_desk_staff;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hotelmanagementsystem.backend.mapper.LiveOrderRecordMapper;
import com.hotelmanagementsystem.backend.mapper.ReservationRecordMapper;
import com.hotelmanagementsystem.backend.mapper.RoomMapper;
import com.hotelmanagementsystem.backend.pojo.Room;
import com.hotelmanagementsystem.backend.service.inter.administrator.front_desk_staff.RemoveRoomService;
import com.hotelmanagementsystem.backend.utils.check.logic_check.LogicCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RemoveRoomServiceImpl implements RemoveRoomService {
    private final RoomMapper roomMapper;
    private final LiveOrderRecordMapper liveOrderRecordMapper;
    private final ReservationRecordMapper reservationRecordMapper;
    
    @Autowired
    public RemoveRoomServiceImpl(RoomMapper roomMapper, LiveOrderRecordMapper liveOrderRecordMapper, ReservationRecordMapper reservationRecordMapper) {
        this.roomMapper = roomMapper;
        this.liveOrderRecordMapper = liveOrderRecordMapper;
        this.reservationRecordMapper = reservationRecordMapper;
    }
    
    @Override
    public Map<String, String> removeRoom(String number) {
        Map<String,String> map = new HashMap<>();
        // 逻辑检查
        if (LogicCheck.isRoomBeingUsed(number, reservationRecordMapper, liveOrderRecordMapper)) {
            map.put("message", "该客房正在被使用，拒绝操作！");
            return map;
        }
        //删除客房
        QueryWrapper<Room> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("number",number);
        roomMapper.delete(queryWrapper);
        map.put("message","success");
        return map;
    }
}
