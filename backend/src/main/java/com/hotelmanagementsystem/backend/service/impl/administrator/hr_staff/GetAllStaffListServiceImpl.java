package com.hotelmanagementsystem.backend.service.impl.administrator.hr_staff;

import com.hotelmanagementsystem.backend.mapper.StaffMapper;
import com.hotelmanagementsystem.backend.service.inter.administrator.hr_staff.GetAllStaffListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GetAllStaffListServiceImpl implements GetAllStaffListService {
    
    private final StaffMapper staffMapper;
    
    @Autowired
    public GetAllStaffListServiceImpl(StaffMapper staffMapper) {
        this.staffMapper = staffMapper;
    }

    @Override
    public Map<String, Object> getList() {
        Map<String, Object> map = new HashMap<>();
        map.put("message", "success");
        map.put("staffs", staffMapper.selectList(null));
        return map;
    }
}
