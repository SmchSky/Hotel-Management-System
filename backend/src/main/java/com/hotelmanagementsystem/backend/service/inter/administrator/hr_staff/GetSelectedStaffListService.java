package com.hotelmanagementsystem.backend.service.inter.administrator.hr_staff;

import java.util.Map;

public interface GetSelectedStaffListService {
    Map<String, Object> getList(Map<String, String> data);
}
