package com.hotelmanagementsystem.backend.service.impl.administrator.front_desk_staff;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hotelmanagementsystem.backend.mapper.RoomMapper;
import com.hotelmanagementsystem.backend.pojo.Room;
import com.hotelmanagementsystem.backend.service.inter.administrator.front_desk_staff.AddRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AddRoomServiceImpl implements AddRoomService {
    @Autowired
    private RoomMapper roomMapper;
    @Override
    public Map<String, String> addRoom(Map<String, String> data) {
        //返回的Map
        Map<String,String> map = new HashMap<>();
        //取出信息
        String number = data.get("number");
        String type = data.get("type");
        String area = data.get("area");
        String price = data.get("price");

        //合法性判断
        if(number.length() == 0){
            map.put("error_message", "房间编号不能为空！");
            return map;
        }
        if(number.length() != 10){
            map.put("error_message", "房间编号长度必须为10！");
            return map;
        }
        if(area.length() == 0){
            map.put("error_message", "房间面积不能为空！");
            return map;
        }
        if(price.length() == 0){
            map.put("error_message", "房间价格不能为空！");
            return map;
        }

        //根据房间编号进行重复性检查
        QueryWrapper<Room> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("number",number);
        List<Room> list = roomMapper.selectList(queryWrapper);
        if(!list.isEmpty()){
            map.put("error_message", "房间编号已存在");
            return map;
        }

        //插入新数据
        Room room_new = new Room(number,type,Integer.parseInt(area),Integer.parseInt(price));
        roomMapper.insert(room_new);
        map.put("error_message", "success");
        return map;
    }
}
