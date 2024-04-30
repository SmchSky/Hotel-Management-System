package com.hotelmanagementsystem.backend.service.impl.administrator.front_desk_staff;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hotelmanagementsystem.backend.mapper.LiveOrderRecordMapper;
import com.hotelmanagementsystem.backend.mapper.ReservationRecordMapper;
import com.hotelmanagementsystem.backend.pojo.LiveOrderRecord;
import com.hotelmanagementsystem.backend.pojo.ReservationRecord;
import com.hotelmanagementsystem.backend.pojo.Room;
import com.hotelmanagementsystem.backend.service.inter.administrator.front_desk_staff.GetAllRoomListService;
import com.hotelmanagementsystem.backend.service.inter.administrator.front_desk_staff.GetSelectedRoomListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.*;


@Service
public class GetSelectedRoomListServiceImpl implements GetSelectedRoomListService {
    
    @Autowired
    private LiveOrderRecordMapper liveOrderRecordMapper;
    @Autowired
    private ReservationRecordMapper reservationRecordMapper;
    
    @Autowired
    private GetAllRoomListService getAllRoomListService;
    
    @Override
    public Map<String, Object> getRoomList(Map<String, String> data) {
        // 用于返回的map
        Map<String, Object> map = new HashMap<>();
        // 从room表中取出所有元组
        List<Room> list = getAllRoomListService.getRoomList();
        // 取出信息
        String checkin_time = data.get("checkin_time");
        String latest_leave_time = data.get("checkout_time");
        
        // 合法性检验
        if (checkin_time.length() == 0) {
            map.put("error_message", "入住日期不可为空！");
            return map;
        }
        if (latest_leave_time.length() == 0) {
            map.put("error_message", "预计离开日期不可为空！");
            return map;
        }
        // 将checkin_time和latest_leave_time转换为Date类型
        Date a = Date.valueOf(checkin_time);
        Date b = Date.valueOf(latest_leave_time);
        
        // 判断checkin_date是否在latest_leave_date后面
        if (a.after(b) || a.equals(b)) {
            map.put("error_message", "预计离开日期要晚于入住日期！");
            return map;
        }
        
        
        // 根据信息检索满足条件的房间列表并返回
        
        // 从订单表选择已入住的元组
        QueryWrapper<LiveOrderRecord> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("status", "已入住");
        
        // liveOrderRecordList中的元组对应的room是要从list中删除的
        List<LiveOrderRecord> liveOrderRecordList = liveOrderRecordMapper.selectList(queryWrapper1);
        Iterator<LiveOrderRecord> iterator_live_record = liveOrderRecordList.iterator();
        
        
        // 将liveOrderRecordList遍历，完成对其的检索
        while (iterator_live_record.hasNext()) {
            // 取出元素
            LiveOrderRecord element = iterator_live_record.next();
            // 定义A和B
            Date A = element.getCheckinDate();
            Date B = element.getLatestLeaveDate();
            if (a.after(B) || A.after(b)) {
                // 将该订单记录从liveOrderRecordList中移除
                // 删除选中的元素
                iterator_live_record.remove();
            }
        }
        
        // 从list中移除房间编号等于liveOrderRecordList中的对象的房间编号的对象
        for (LiveOrderRecord liveOrderRecord : liveOrderRecordList) {
            // 新建迭代器
            Iterator<Room> iterator_room = list.iterator();
            String room_number = liveOrderRecord.getRoomNumber();
            while (iterator_room.hasNext()) {
                Room room = iterator_room.next();
                if (room.getNumber().equals(room_number)) {
                    // 删除选中的元素
                    iterator_room.remove();
                    break;
                }
            }
        }
        
        // 从预约表选择等待入住的元组
        QueryWrapper<ReservationRecord> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("status", "等待入住");
        
        // reservationRecordList中的元组对应的room是要从list中删除的
        List<ReservationRecord> reservationRecordList = reservationRecordMapper.selectList(queryWrapper2);
        Iterator<ReservationRecord> iterator_reserve_record = reservationRecordList.iterator();
        
        // 将reservationRecordList遍历，完成对其的检索
        while (iterator_reserve_record.hasNext()) {
            ReservationRecord element = iterator_reserve_record.next();
            Date A = element.getCheckinDate();
            Date B = element.getLatestLeaveDate();
            if (a.after(B) || A.after(b)) {
                // 将该订单记录从reservationRecordList中移除
                iterator_reserve_record.remove();
            }
        }
        
        // 从list中移除房间编号等于reservationRecordList中的对象的房间编号的对象
        for (ReservationRecord reservationRecord : reservationRecordList) {
            // 新建迭代器
            Iterator<Room> iterator_room = list.iterator();
            String room_number = reservationRecord.getRoomNumber();
            while (iterator_room.hasNext()) {
                Room room = iterator_room.next();
                if (room.getNumber().equals(room_number)) {
                    iterator_room.remove();
                }
            }
        }
        // 定义返回map
        map.put("error_message", "success");
        map.put("selected_rooms", list);
        return map;
    }
}
