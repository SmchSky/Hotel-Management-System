package com.hotelmanagementsystem.backend.service.inter.administrator.front_desk_staff;

import java.util.Map;

public interface GetSelectedRoomListService {
    Map<String,Object> getRoomList(Map<String,String> data);
}
