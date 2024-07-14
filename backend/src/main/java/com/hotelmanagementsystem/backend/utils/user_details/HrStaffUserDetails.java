package com.hotelmanagementsystem.backend.utils.user_details;

import com.hotelmanagementsystem.backend.pojo.HrStaffUser;
import com.hotelmanagementsystem.backend.pojo.User;
import lombok.AllArgsConstructor;

/**
 * HrStaff的UserDetails实现类
 */
@AllArgsConstructor
public class HrStaffUserDetails extends BaseUserDetails<HrStaffUser> {

    private HrStaffUser hrStaffUser;
    
    private String role;
    
    @Override
    public HrStaffUser getUser() {
        return hrStaffUser;
    }
    
    @Override
    public String getRole() {
        return role;
    }
}
