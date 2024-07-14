package com.hotelmanagementsystem.backend.utils.user_details;

import com.hotelmanagementsystem.backend.pojo.FrontDeskStaffUser;
import com.hotelmanagementsystem.backend.pojo.User;
import lombok.AllArgsConstructor;

/**
 * FrontDeskStaff的UserDetails实现类
 */
@AllArgsConstructor
public class FrontDeskStaffUserDetails extends BaseUserDetails<FrontDeskStaffUser> {
    
    private FrontDeskStaffUser frontDeskStaffUser;
    
    private String role;
    
    @Override
    public FrontDeskStaffUser getUser() {
        return frontDeskStaffUser;
    }
    
    @Override
    public String getRole() {
        return role;
    }
}
