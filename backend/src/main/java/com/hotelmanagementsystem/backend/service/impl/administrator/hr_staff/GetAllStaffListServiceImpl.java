package com.hotelmanagementsystem.backend.service.impl.administrator.hr_staff;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hotelmanagementsystem.backend.mapper.RoomMapper;
import com.hotelmanagementsystem.backend.mapper.StaffMapper;
import com.hotelmanagementsystem.backend.pojo.Room;
import com.hotelmanagementsystem.backend.pojo.Staff;
import com.hotelmanagementsystem.backend.service.inter.administrator.hr_staff.GetAllStaffListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GetAllStaffListServiceImpl implements GetAllStaffListService {
    @Autowired
    private StaffMapper staffMapper;

    @Override
    public Map<String, Object> getList() {
        //返回的map
        Map<String, Object> map = new HashMap<>();
        //用于返回的rooms
        List<Staff> list;
        //在表中查询
        QueryWrapper<Staff> queryWrapper = new QueryWrapper<>();
        list = staffMapper.selectList(queryWrapper);
        //返回结果
        map.put("error_message", "success");
        map.put("staffs", list);
        return map;
    }
}
