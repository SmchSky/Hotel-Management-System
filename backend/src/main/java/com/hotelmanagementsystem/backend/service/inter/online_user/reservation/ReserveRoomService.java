package com.hotelmanagementsystem.backend.service.inter.online_user.reservation;

import java.util.Map;

public interface ReserveRoomService {
    Map<String, String> reserve(Map<String,String> data);
}
