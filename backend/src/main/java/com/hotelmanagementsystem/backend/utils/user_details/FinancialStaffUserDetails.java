package com.hotelmanagementsystem.backend.utils.user_details;

import com.hotelmanagementsystem.backend.pojo.FinancialStaffUser;
import com.hotelmanagementsystem.backend.pojo.User;
import lombok.AllArgsConstructor;

/**
 * FinancialStaff的UserDetails实现类
 */
@AllArgsConstructor
public class FinancialStaffUserDetails extends BaseUserDetails<FinancialStaffUser> {

    private FinancialStaffUser financialStaffUser;
    
    private String role;
    
    @Override
    public FinancialStaffUser getUser() {
        return financialStaffUser;
    }
    
    @Override
    public String getRole() {
        return role;
    }
}
