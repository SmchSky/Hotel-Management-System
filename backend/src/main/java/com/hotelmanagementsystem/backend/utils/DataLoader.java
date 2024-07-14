package com.hotelmanagementsystem.backend.utils;

import com.hotelmanagementsystem.backend.service.inter.administrator.front_desk_staff.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    
    private final RoomService roomService;
    
    @Autowired
    public DataLoader(RoomService roomService) {
        this.roomService = roomService;
    }
    
    /**
     * 在应用启动时将房间信息加载到缓存中
     * @param args 命令行参数
     */
    @Override
    public void run(String... args) throws Exception {
        roomService.loadAllRoomsToCache();
    }
}