package com.hotelmanagementsystem.backend.service.impl.administrator.front_desk_staff;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hotelmanagementsystem.backend.mapper.LiveOrderRecordMapper;
import com.hotelmanagementsystem.backend.mapper.ReservationRecordMapper;
import com.hotelmanagementsystem.backend.mapper.RoomMapper;
import com.hotelmanagementsystem.backend.pojo.LiveOrderRecord;
import com.hotelmanagementsystem.backend.pojo.ReservationRecord;
import com.hotelmanagementsystem.backend.pojo.Room;
import com.hotelmanagementsystem.backend.service.inter.administrator.front_desk_staff.RoomService;
import com.hotelmanagementsystem.backend.utils.check.info_valid_check.QueryRoomInfoValidCheck;
import com.hotelmanagementsystem.backend.utils.check.info_valid_check.RoomInfoValidCheck;
import com.hotelmanagementsystem.backend.utils.check.logic_check.LogicCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class RoomServiceImpl implements RoomService {
    
    private final RoomMapper roomMapper;
    private final LiveOrderRecordMapper liveOrderRecordMapper;
    private final ReservationRecordMapper reservationRecordMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    
    private static final String ROOM_KEY_PREFIX = "room:";
    
    @Autowired
    public RoomServiceImpl(RoomMapper roomMapper, LiveOrderRecordMapper liveOrderRecordMapper, ReservationRecordMapper reservationRecordMapper, RedisTemplate<String, Object> redisTemplate) {
        this.roomMapper = roomMapper;
        this.liveOrderRecordMapper = liveOrderRecordMapper;
        this.reservationRecordMapper = reservationRecordMapper;
        this.redisTemplate = redisTemplate;
    }
    
    /**
     * 将房间信息加载到redis缓存中
     */
    public void loadAllRoomsToCache() {
        List<Room> rooms = roomMapper.selectList(null);
        for (Room room : rooms) {
            redisTemplate.opsForValue().set(ROOM_KEY_PREFIX + room.getNumber(), room);
        }
    }
    
    /**
     * 新增房间信息
     *
     * @param data 新增房间的数据
     * @return 新增结果
     */
    @Override
    public Map<String, String> addRoom(Map<String, String> data) {
        Map<String, String> map = new HashMap<>();
        String number = data.get("number");
        String type = data.get("type");
        String area = data.get("area");
        String price = data.get("price");
        // 合法性检验
        String message = RoomInfoValidCheck.checkRoomInfoValid(number, type, area, price);
        if (!message.equals("success")) {
            map.put("message", message);
            return map;
        }
        // 房间编号重复性检查
        QueryWrapper<Room> queryWrapper = new QueryWrapper<>();
        List<Room> list = roomMapper.selectList(queryWrapper.eq("number", number));
        if (!list.isEmpty()) {
            map.put("message", "房间编号已存在");
            return map;
        }
        // 向数据库中插入新数据
        Room new_room = new Room(number, type, Integer.parseInt(area), Integer.parseInt(price));
        roomMapper.insert(new_room);
        // 在缓存中新增改房间的信息
        redisTemplate.opsForValue().set(ROOM_KEY_PREFIX + number, new_room);
        map.put("message", "success");
        return map;
    }
    
    /**
     * 在redis缓存中读取所有房间信息
     *
     * @return 缓存中的所有房间信息
     */
    @Override
    public List<Room> getAllRoomList() {
        List<Room> rooms = new ArrayList<>();
        ScanOptions scanOptions = ScanOptions.scanOptions().match(ROOM_KEY_PREFIX + "*").count(1000).build();
        try (Cursor<String> cursor = redisTemplate.scan(scanOptions)) {
            while (cursor.hasNext()) {
                String key = cursor.next();
                Room room = (Room) redisTemplate.opsForValue().get(key);
                if (room != null) {
                    rooms.add(room);
                }
            }
        } catch (Exception e) {
            System.out.println("在缓存中读取房间信息失败！");
            return null;
        }
        return rooms;
    }
    
    /**
     * 根据入住时间和离开时间查询所有满足条件的房间信息（该类的逻辑未修改，留待以后有时间修改！）
     *
     * @param data 查询条件
     * @return 满足条件的房间信息
     */
    @Override
    public Map<String, Object> getSelectedRoomList(Map<String, String> data) {
        Map<String, Object> map = new HashMap<>();
        String checkinTime = data.get("checkin_time");
        String latestLeaveTime = data.get("checkout_time");
        LocalDate a = LocalDate.parse(checkinTime);
        LocalDate b = LocalDate.parse(latestLeaveTime);
        // 合法性检验
        String message = QueryRoomInfoValidCheck.checkQueryRoomInfoValid(checkinTime, latestLeaveTime);
        if (!message.equals("success")) {
            map.put("message", message);
            return map;
        }
        // 在缓存中获取全部的房间信息
        List<Room> list = getAllRoomList();
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
    
    /**
     * 删除房间信息
     *
     * @param number 房间编号
     * @return 删除结果
     */
    @Override
    public Map<String, String> removeRoom(String number) {
        Map<String, String> map = new HashMap<>();
        // 逻辑检查
        if (LogicCheck.isRoomBeingUsed(number, reservationRecordMapper, liveOrderRecordMapper)) {
            map.put("message", "该客房正在被使用，拒绝操作！");
            return map;
        }
        // 先删除缓存中的客房信息（防止极端情况出现）
        redisTemplate.delete(ROOM_KEY_PREFIX + number);
        // 再删除数据库中的客房信息
        QueryWrapper<Room> queryWrapper = new QueryWrapper<>();
        roomMapper.delete(queryWrapper.eq("number", number));
        map.put("message", "success");
        return map;
    }
    
    /**
     * 更新房间信息
     *
     * @param data 更新房间的数据
     * @return 更新结果
     */
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
        if (!new_number.equals(origin_number)) {
            QueryWrapper<Room> queryWrapper = new QueryWrapper<>();
            List<Room> list = roomMapper.selectList(queryWrapper.eq("number", new_number));
            if (!list.isEmpty()) {
                map.put("message", "房间编号已存在");
                return map;
            }
        }
        // 更新后的房间信息
        Room new_room = new Room(new_number, type, Integer.parseInt(area), Integer.parseInt(price));
        // 先更新缓存中的数据（删除原来的记录，再新增新的记录）
        redisTemplate.delete(ROOM_KEY_PREFIX + origin_number);
        redisTemplate.opsForValue().set(ROOM_KEY_PREFIX + new_room.getNumber(), new_room);
        // 再更新数据库中的数据
        UpdateWrapper<Room> updateWrapper = new UpdateWrapper<>();
        roomMapper.update(new_room, updateWrapper.eq("number", origin_number));
        map.put("message", "success");
        return map;
    }
}
