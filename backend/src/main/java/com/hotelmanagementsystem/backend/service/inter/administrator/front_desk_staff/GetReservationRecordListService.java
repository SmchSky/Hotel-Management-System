package com.hotelmanagementsystem.backend.service.inter.administrator.front_desk_staff;

import java.util.Map;

public interface GetReservationRecordListService {
    Map<String, Object> getList(Map<String, String> data);
}
