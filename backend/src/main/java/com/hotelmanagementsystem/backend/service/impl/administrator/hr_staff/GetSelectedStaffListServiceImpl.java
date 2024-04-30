package com.hotelmanagementsystem.backend.service.impl.administrator.hr_staff;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hotelmanagementsystem.backend.mapper.StaffMapper;
import com.hotelmanagementsystem.backend.pojo.Staff;
import com.hotelmanagementsystem.backend.service.inter.administrator.hr_staff.GetAllStaffListService;
import com.hotelmanagementsystem.backend.service.inter.administrator.hr_staff.GetSelectedStaffListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GetSelectedStaffListServiceImpl implements GetSelectedStaffListService {
    @Autowired
    private StaffMapper staffMapper;
    @Autowired
    private GetAllStaffListService getAllStaffListService;

    @Override
    public Map<String, Object> getList(Map<String, String> data) {
        //定义返回的map
        Map<String, Object> map = new HashMap<>();
        //取出data的数据
        String number = data.get("number");

        //如果number为空，则返回全部员工信息
        if (number.length() == 0) {
            return getAllStaffListService.getList();
        }

        if (number.length() != 10) {
            map.put("error_message", "员工编号格式错误！");
            return map;
        }
        QueryWrapper<Staff> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("number", number);
        List<Staff> list = staffMapper.selectList(queryWrapper);
        if (list.isEmpty()) {
            map.put("error_message", "未找到员工信息！");
            return map;
        }
        map.put("error_message", "success");
        map.put("staffs", list);

        return map;
    }
}
