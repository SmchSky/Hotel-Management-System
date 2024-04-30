package com.hotelmanagementsystem.backend.service.impl.administrator.front_desk_staff;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hotelmanagementsystem.backend.mapper.LiveOrderRecordMapper;
import com.hotelmanagementsystem.backend.mapper.ReservationRecordMapper;
import com.hotelmanagementsystem.backend.mapper.RoomMapper;
import com.hotelmanagementsystem.backend.pojo.LiveOrderRecord;
import com.hotelmanagementsystem.backend.pojo.ReservationRecord;
import com.hotelmanagementsystem.backend.pojo.Room;
import com.hotelmanagementsystem.backend.service.inter.administrator.front_desk_staff.RemoveRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RemoveRoomServiceImpl implements RemoveRoomService {
    @Autowired
    private RoomMapper roomMapper;
    @Autowired
    private LiveOrderRecordMapper liveOrderRecordMapper;
    @Autowired
    private ReservationRecordMapper reservationRecordMapper;
    @Override
    public Map<String, String> removeRoom(String number) {
        //返回用的map
        Map<String,String> map = new HashMap<>();

        //如果该客房对应的住宿预约表的记录的状态处于“等待入住”或者对应的住宿订单表的状态处于“已入住”，应拒绝更新操作
        List<LiveOrderRecord> list_1;
        QueryWrapper<LiveOrderRecord> queryWrapper_1 = new QueryWrapper<>();
        queryWrapper_1.eq("room_number", number).eq("status", "已入住");
        list_1 = liveOrderRecordMapper.selectList(queryWrapper_1);
        if (!list_1.isEmpty()) {
            //存在这样的记录，拒绝更新操作
            map.put("error_message", "该客房正在被使用，拒绝操作！");
            return map;
        }
        List<ReservationRecord> list_2;
        QueryWrapper<ReservationRecord> queryWrapper_2 = new QueryWrapper<>();
        queryWrapper_2.eq("room_number", number).eq("status", "等待入住");
        list_2 = reservationRecordMapper.selectList(queryWrapper_2);
        if(!list_2.isEmpty()){
            //存在这样的记录，拒绝更新操作
            map.put("error_message", "该客房正在被使用，拒绝操作！");
            return map;
        }

        //删除客房
        QueryWrapper<Room> queryWrapper = new QueryWrapper<Room>();
        queryWrapper.eq("number",number);
        roomMapper.delete(queryWrapper);
        map.put("error_message","success");
        return map;
    }
}
