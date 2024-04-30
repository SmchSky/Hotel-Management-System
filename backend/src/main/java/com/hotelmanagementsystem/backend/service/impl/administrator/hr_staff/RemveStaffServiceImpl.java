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
public class RemveStaffServiceImpl implements RemoveStaffService {
    @Autowired
    private StaffMapper staffMapper;

    @Override
    public Map<String, String> removeStaff(Map<String, String> data) {
        //定义返回的map
        Map<String, String> map = new HashMap<>();
        //取出data的数据
        String number = data.get("number");
        //找到要删除的员工
        QueryWrapper<Staff> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("number", number);
        staffMapper.delete(queryWrapper);
        //返回信息
        map.put("error_message", "success");
        return map;
    }
}
