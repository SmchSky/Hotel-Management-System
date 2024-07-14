package com.hotelmanagementsystem.backend.service.impl.administrator.front_desk_staff;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hotelmanagementsystem.backend.mapper.LiveOrderRecordMapper;
import com.hotelmanagementsystem.backend.mapper.ReservationRecordMapper;
import com.hotelmanagementsystem.backend.mapper.RoomMapper;
import com.hotelmanagementsystem.backend.pojo.Room;
import com.hotelmanagementsystem.backend.service.inter.administrator.front_desk_staff.UpdateRoomService;
import com.hotelmanagementsystem.backend.utils.check.info_valid_check.RoomInfoValidCheck;
import com.hotelmanagementsystem.backend.utils.check.logic_check.LogicCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UpdateRoomServiceImpl implements UpdateRoomService {
    
    private final RoomMapper roomMapper;
    private final LiveOrderRecordMapper liveOrderRecordMapper;
    private final ReservationRecordMapper reservationRecordMapper;
    
    @Autowired
    public UpdateRoomServiceImpl(RoomMapper roomMapper, LiveOrderRecordMapper liveOrderRecordMapper, ReservationRecordMapper reservationRecordMapper) {
        this.roomMapper = roomMapper;
        this.liveOrderRecordMapper = liveOrderRecordMapper;
        this.reservationRecordMapper = reservationRecordMapper;
    }
    
    @Override
    public Map<String, String> updateRoom(Map<String, String> data) {
        Map<String, String> map = new HashMap<>();
        String origin_number = data.get("number_origin");
        String new_number = data.get("number");
        String type = data.get("type");
        String area = data.get("area");
        String price = data.get("price");
        // 合法性检验
        String message = RoomInfoValidCheck.checkRoomInfoValid(new_number, type, area, price);
        if (!message.equals("success")) {
            map.put("message", message);
            return map;
        }
        // 逻辑检查
        if (LogicCheck.isRoomBeingUsed(origin_number, reservationRecordMapper, liveOrderRecordMapper)) {
            map.put("message", "该客房正在被使用，拒绝操作！");
            return map;
        }
        // 如果更新了房间编号则需要进行重复性检查，不更新则不需要
        if(!new_number.equals(origin_number)){
            QueryWrapper<Room> queryWrapper = new QueryWrapper<>();
            List<Room> list = roomMapper.selectList(queryWrapper.eq("number", new_number));
            if (!list.isEmpty()) {
                map.put("message", "房间编号已存在");
                return map;
            }
        }
        // 更新数据
        UpdateWrapper<Room> updateWrapper = new UpdateWrapper<>();
        Room new_room = new Room(new_number, type, Integer.parseInt(area), Integer.parseInt(price));
        updateWrapper.eq("number", origin_number);
        roomMapper.update(new_room, updateWrapper);
        map.put("message", "success");
        return map;
    }
}
