package com.hotelmanagementsystem.backend.service.impl.administrator.front_desk_staff;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hotelmanagementsystem.backend.mapper.RoomMapper;
import com.hotelmanagementsystem.backend.pojo.Room;
import com.hotelmanagementsystem.backend.service.inter.administrator.front_desk_staff.AddRoomService;
import com.hotelmanagementsystem.backend.utils.check.info_valid_check.RoomInfoValidCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AddRoomServiceImpl implements AddRoomService {
    
    private final RoomMapper roomMapper;
    
    @Autowired
    public AddRoomServiceImpl(RoomMapper roomMapper) {
        this.roomMapper = roomMapper;
    }
    
    @Override
    public Map<String, String> addRoom(Map<String, String> data) {
        Map<String, String> map = new HashMap<>();
        String number = data.get("number");
        String type = data.get("type");
        String area = data.get("area");
        String price = data.get("price");
        // 合法性检验
        String message = RoomInfoValidCheck.checkRoomInfoValid(number, type, area, price);
        if(!message.equals("success")){
            map.put("message", message);
            return map;
        }
        // 房间编号重复性检查
        QueryWrapper<Room> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("number", number);
        List<Room> list = roomMapper.selectList(queryWrapper);
        if (!list.isEmpty()) {
            map.put("message", "房间编号已存在");
            return map;
        }
        // 插入新数据
        Room room_new = new Room(number, type, Integer.parseInt(area), Integer.parseInt(price));
        roomMapper.insert(room_new);
        map.put("message", "success");
        return map;
    }
}
