package com.hotelmanagementsystem.backend.service.impl.administrator.front_desk_staff;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hotelmanagementsystem.backend.mapper.RoomMapper;
import com.hotelmanagementsystem.backend.pojo.Room;
import com.hotelmanagementsystem.backend.service.inter.administrator.front_desk_staff.GetAllRoomListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllRoomListServiceImpl implements GetAllRoomListService {
    
    private final RoomMapper roomMapper;
    
    @Autowired
    public GetAllRoomListServiceImpl(RoomMapper roomMapper) {
        this.roomMapper = roomMapper;
    }
    
    @Override
    public List<Room> getRoomList() {
        QueryWrapper<Room> queryWrapper = new QueryWrapper<>();
        return roomMapper.selectList(queryWrapper);
    }
}
