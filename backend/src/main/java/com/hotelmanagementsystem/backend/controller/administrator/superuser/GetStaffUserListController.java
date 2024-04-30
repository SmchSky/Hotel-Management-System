package com.hotelmanagementsystem.backend.controller.administrator.superuser;

import com.hotelmanagementsystem.backend.pojo.StaffUser;
import com.hotelmanagementsystem.backend.service.inter.administrator.superuser.GetStaffUserListSercvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetStaffUserListController {
    @Autowired
    private GetStaffUserListSercvice getListService;

    //由于不需要修改数据库，故使用GetMapping
    @GetMapping("/superuser/get_staff_user_list/")
    public List<StaffUser> getStaffUserList() {
        return getListService.getStaffUserList();
    }
}
