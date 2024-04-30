package com.hotelmanagementsystem.backend.service.inter.administrator.front_desk_staff;

import java.util.Map;

public interface CheckInService {
    Map<String, String> checkIn(Map<String,String> data);
}
