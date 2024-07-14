package com.hotelmanagementsystem.backend.service.impl.administrator.front_desk_staff;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hotelmanagementsystem.backend.mapper.LiveOrderRecordMapper;
import com.hotelmanagementsystem.backend.mapper.ReservationRecordMapper;
import com.hotelmanagementsystem.backend.pojo.LiveOrderRecord;
import com.hotelmanagementsystem.backend.pojo.ReservationRecord;
import com.hotelmanagementsystem.backend.pojo.Room;
import com.hotelmanagementsystem.backend.service.inter.administrator.front_desk_staff.GetAllRoomListService;
import com.hotelmanagementsystem.backend.service.inter.administrator.front_desk_staff.GetSelectedRoomListService;
import com.hotelmanagementsystem.backend.utils.check.info_valid_check.QueryRoomInfoValidCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 该类的逻辑未修改，留待以后有时间修改
 */
@Service
public class GetSelectedRoomListServiceImpl implements GetSelectedRoomListService {
    
    private final LiveOrderRecordMapper liveOrderRecordMapper;
    private final ReservationRecordMapper reservationRecordMapper;
    private final GetAllRoomListService getAllRoomListService;
    
    @Autowired
    public GetSelectedRoomListServiceImpl(LiveOrderRecordMapper liveOrderRecordMapper, ReservationRecordMapper reservationRecordMapper, GetAllRoomListService getAllRoomListService) {
        this.liveOrderRecordMapper = liveOrderRecordMapper;
        this.reservationRecordMapper = reservationRecordMapper;
        this.getAllRoomListService = getAllRoomListService;
    }
    
    @Override
    public Map<String, Object> getRoomList(Map<String, String> data) {
        Map<String, Object> map = new HashMap<>();
        String checkinTime = data.get("checkin_time");
        String latestLeaveTime = data.get("checkout_time");
        LocalDate a = LocalDate.parse(checkinTime);
        LocalDate b = LocalDate.parse(latestLeaveTime);
        
        // 合法性检验
        String message = QueryRoomInfoValidCheck.checkQueryRoomInfoValid(checkinTime, latestLeaveTime);
        if(!message.equals("success")){
            map.put("message", message);
            return map;
        }
        
        // 先获取全部的房间列表
        List<Room> list = getAllRoomListService.getRoomList();
        // 根据条件查询房间列表
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
            LocalDate A = element.getCheckinDate();
            LocalDate B = element.getLatestLeaveDate();
            if (a.isAfter(B) || A.isAfter(b)) {
                // 将该订单记录从liveOrderRecordList中移除
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
            LocalDate A = element.getCheckinDate();
            LocalDate B = element.getLatestLeaveDate();
            if (a.isAfter(B) || A.isAfter(b)) {
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
        map.put("message", "success");
        map.put("selected_rooms", list);
        return map;
    }
}
