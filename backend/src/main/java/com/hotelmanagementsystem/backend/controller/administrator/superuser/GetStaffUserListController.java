package com.hotelmanagementsystem.backend.controller.administrator.superuser;

import com.hotelmanagementsystem.backend.pojo.User;
import com.hotelmanagementsystem.backend.service.inter.administrator.superuser.GetStaffUserListService;
import com.hotelmanagementsystem.backend.utils.user_details.BaseUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/hotel")
public class GetStaffUserListController {
    
    private final GetStaffUserListService getListService;
    
    public GetStaffUserListController(GetStaffUserListService getListService) {
        this.getListService = getListService;
    }

    @GetMapping("/superuser/get_staff_user_list")
    public List<User> getStaffUserList() {
        // 身份验证
        String role = ((BaseUserDetails<?>) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getRole();
        if (!role.equals("超级用户")) {
            return null;
        }
        return getListService.getStaffUserList();
    }
}
