package com.hotelmanagementsystem.backend.service.inter.administrator.front_desk_staff;

import com.hotelmanagementsystem.backend.pojo.Room;

import java.util.List;
import java.util.Map;

public interface RoomService {
    
    void loadAllRoomsToCache();
    
    Map<String, String> addRoom(Map<String, String> data);
    
    List<Room> getAllRoomList();
    
    Map<String, Object> getSelectedRoomList(Map<String, String> data);
    
    Map<String, String> removeRoom(String number);
    
    Map<String, String> updateRoom(Map<String, String> data);
    
}
