package com.hotelmanagementsystem.backend.service.impl.administrator.hr_staff;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hotelmanagementsystem.backend.mapper.StaffMapper;
import com.hotelmanagementsystem.backend.pojo.Staff;
import com.hotelmanagementsystem.backend.service.inter.administrator.hr_staff.GetAllStaffListService;
import com.hotelmanagementsystem.backend.service.inter.administrator.hr_staff.QueryStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QueryStaffServiceImpl implements QueryStaffService {
    
    private final StaffMapper staffMapper;
    private final GetAllStaffListService getAllStaffListService;
    
    @Autowired
    public QueryStaffServiceImpl(StaffMapper staffMapper, GetAllStaffListService getAllStaffListService) {
        this.staffMapper = staffMapper;
        this.getAllStaffListService = getAllStaffListService;
    }
    
    @Override
    public Map<String, Object> queryStaff(Map<String, String> data) {
        Map<String, Object> map = new HashMap<>();
        String number = data.get("number");
        // 如果number为空，则返回全部员工信息
        if (number.trim().isEmpty()) {
            return getAllStaffListService.getList();
        }
        // 合法性检验
        if (number.length() != 10) {
            map.put("message", "员工编号格式错误！");
            return map;
        }
        QueryWrapper<Staff> queryWrapper = new QueryWrapper<>();
        List<Staff> staffs = staffMapper.selectList(queryWrapper.eq("number", number));
        if (staffs.isEmpty()) {
            map.put("message", "未找到员工信息！");
            return map;
        }
        map.put("message", "success");
        map.put("staffs", staffs);
        return map;
    }
}
