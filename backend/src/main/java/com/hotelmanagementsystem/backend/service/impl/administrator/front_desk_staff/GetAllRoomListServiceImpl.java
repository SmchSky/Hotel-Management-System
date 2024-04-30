package com.hotelmanagementsystem.backend.service.impl.administrator.front_desk_staff;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hotelmanagementsystem.backend.mapper.RoomMapper;
import com.hotelmanagementsystem.backend.pojo.Room;
import com.hotelmanagementsystem.backend.service.inter.administrator.front_desk_staff.GetAllRoomListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetAllRoomListServiceImpl implements GetAllRoomListService {
    
    @Autowired
    private RoomMapper roomMapper;
    
    @Override
    public List<Room> getRoomList() {
        // 定义查询器
        QueryWrapper<Room> queryWrapper = new QueryWrapper<>();
        // 返回结果
        return new ArrayList<>(roomMapper.selectList(queryWrapper));
    }
}
