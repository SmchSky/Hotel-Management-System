package com.hotelmanagementsystem.backend.service.inter.administrator.financial_staff;

import com.hotelmanagementsystem.backend.pojo.Staff;

import java.util.Map;

public interface QueryStaffService {
    Map<String,Object> queryStaff(Map<String, String> data);
}
