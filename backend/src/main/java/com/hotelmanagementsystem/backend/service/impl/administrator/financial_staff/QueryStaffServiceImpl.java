package com.hotelmanagementsystem.backend.service.impl.administrator.financial_staff;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hotelmanagementsystem.backend.mapper.StaffMapper;
import com.hotelmanagementsystem.backend.pojo.Staff;
import com.hotelmanagementsystem.backend.service.inter.administrator.financial_staff.QueryStaffService;
import com.hotelmanagementsystem.backend.service.inter.administrator.hr_staff.GetAllStaffListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QueryStaffServiceImpl implements QueryStaffService {
    @Autowired
    private StaffMapper staffMapper;
    @Autowired
    private GetAllStaffListService getAllStaffListService;

    @Override
    public Map<String, Object> queryStaff(Map<String, String> data) {
        //定义返回的map
        Map<String, Object> map = new HashMap<>();
        //取出data的数据
        String number = data.get("number");

        //如果number为空，则返回全部员工信息
        if (number.length() == 0) {
            return getAllStaffListService.getList();
        }
        //合法性检验
        if (number.length() != 10) {
            map.put("error_message", "员工编号格式错误！");
            return map;
        }
        QueryWrapper<Staff> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("number", number);
        List<Staff> staffs = staffMapper.selectList(queryWrapper);
        if (staffs.isEmpty()) {
            map.put("error_message", "未找到员工信息！");
            return map;
        }
        map.put("error_message", "success");
        map.put("staffs", staffs);
        return map;
    }
}
