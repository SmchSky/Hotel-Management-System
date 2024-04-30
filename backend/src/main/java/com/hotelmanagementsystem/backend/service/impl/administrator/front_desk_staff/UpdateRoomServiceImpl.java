package com.hotelmanagementsystem.backend.service.impl.administrator.front_desk_staff;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hotelmanagementsystem.backend.mapper.LiveOrderRecordMapper;
import com.hotelmanagementsystem.backend.mapper.ReservationRecordMapper;
import com.hotelmanagementsystem.backend.mapper.RoomMapper;
import com.hotelmanagementsystem.backend.pojo.LiveOrderRecord;
import com.hotelmanagementsystem.backend.pojo.ReservationRecord;
import com.hotelmanagementsystem.backend.pojo.Room;
import com.hotelmanagementsystem.backend.service.inter.administrator.front_desk_staff.UpdateRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UpdateRoomServiceImpl implements UpdateRoomService {
    @Autowired
    private RoomMapper roomMapper;
    @Autowired
    private LiveOrderRecordMapper liveOrderRecordMapper;
    @Autowired
    private ReservationRecordMapper reservationRecordMapper;

    @Override
    public Map<String, String> updateRoom(Map<String, String> data) {
        //返回用的map
        Map<String, String> map = new HashMap<>();
        //取出data信息
        String number_origin = data.get("number_origin");
        String number = data.get("number");
        String area = data.get("area");
        String price = data.get("price");
        String type = data.get("type");

        //信息合法化检验
        if (number.length() == 0) {
            map.put("error_message", "房间编号不能为空！");
            return map;
        }
        if (number.length() != 10) {
            map.put("error_message", "房间编号长度必须为10！");
            return map;
        }
        if (area.length() == 0) {
            map.put("error_message", "房间面积不能为空！");
            return map;
        }
        if (price.length() == 0) {
            map.put("error_message", "房间价格不能为空！");
            return map;
        }

        //如果该客房对应的住宿预约表的记录的状态处于“等待入住”或者对应的住宿订单表的状态处于“已入住”，应拒绝更新操作
        List<LiveOrderRecord> list_1;
        QueryWrapper<LiveOrderRecord> queryWrapper_1 = new QueryWrapper<>();
        queryWrapper_1.eq("room_number", number_origin).eq("status", "已入住");
        list_1 = liveOrderRecordMapper.selectList(queryWrapper_1);
        if (!list_1.isEmpty()) {
            //存在这样的记录，拒绝更新操作
            map.put("error_message", "该客房正在被使用，拒绝操作！");
            return map;
        }
        List<ReservationRecord> list_2;
        QueryWrapper<ReservationRecord> queryWrapper_2 = new QueryWrapper<>();
        queryWrapper_2.eq("room_number", number_origin).eq("status", "等待入住");
        list_2 = reservationRecordMapper.selectList(queryWrapper_2);
        if(!list_2.isEmpty()){
            //存在这样的记录，拒绝更新操作
            map.put("error_message", "该客房正在被使用，拒绝操作！");
            return map;
        }

        //更改了房间编号
        if (!number.equals(number_origin)) {
            //根据房间编号进行重复性检查
            QueryWrapper<Room> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("number", number);
            List<Room> list = roomMapper.selectList(queryWrapper);
            if (!list.isEmpty()) {
                map.put("error_message", "房间编号已存在");
                return map;
            }
        }

        //更新数据
        UpdateWrapper<Room> updateWrapper = new UpdateWrapper<>();
        Room room_new = new Room(number, type, Integer.parseInt(area), Integer.parseInt(price));
        updateWrapper.eq("number", number_origin);
        roomMapper.update(room_new, updateWrapper);
        map.put("error_message", "success");
        return map;
    }
}
