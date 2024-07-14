package com.hotelmanagementsystem.backend.service.impl.administrator.hr_staff;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hotelmanagementsystem.backend.mapper.StaffMapper;
import com.hotelmanagementsystem.backend.pojo.Staff;
import com.hotelmanagementsystem.backend.service.inter.administrator.hr_staff.RemoveStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RemoveStaffServiceImpl implements RemoveStaffService {
    
    private final StaffMapper staffMapper;
    
    @Autowired
    public RemoveStaffServiceImpl(StaffMapper staffMapper) {
        this.staffMapper = staffMapper;
    }
    
    @Override
    public Map<String, String> removeStaff(Map<String, String> data) {
        Map<String, String> map = new HashMap<>();
        QueryWrapper<Staff> queryWrapper = new QueryWrapper<>();
        staffMapper.delete(queryWrapper.eq("number", data.get("number")));
        map.put("message", "success");
        return map;
    }
}
