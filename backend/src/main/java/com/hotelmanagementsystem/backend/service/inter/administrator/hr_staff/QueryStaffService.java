package com.hotelmanagementsystem.backend.service.inter.administrator.hr_staff;

import java.util.Map;

public interface QueryStaffService {
    Map<String,Object> queryStaff(Map<String, String> data);
}
