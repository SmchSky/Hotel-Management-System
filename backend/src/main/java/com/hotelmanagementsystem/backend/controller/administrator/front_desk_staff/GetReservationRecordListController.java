package com.hotelmanagementsystem.backend.controller.administrator.front_desk_staff;

import com.hotelmanagementsystem.backend.service.inter.administrator.front_desk_staff.GetReservationRecordListService;
import com.hotelmanagementsystem.backend.utils.user_details.BaseUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/hotel")
public class GetReservationRecordListController {
    
    private final GetReservationRecordListService getReservationRecordListService;
    
    @Autowired
    public GetReservationRecordListController(GetReservationRecordListService getReservationRecordListService) {
        this.getReservationRecordListService = getReservationRecordListService;
    }

    @PostMapping("/front_desk_staff/get_reservation_record_list")
    public Map<String, Object> getList(@RequestParam Map<String, String> data) {
        // 身份验证
        String role = ((BaseUserDetails<?>) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getRole();
        if (!role.equals("酒店前台工作人员")) {
            return Map.of("message", "无权限执行该操作！");
        }
        return getReservationRecordListService.getList(data);
    }
}
