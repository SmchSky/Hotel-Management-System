package com.hotelmanagementsystem.backend.utils.user_details;

import com.hotelmanagementsystem.backend.pojo.PurchaseStaffUser;
import com.hotelmanagementsystem.backend.pojo.User;
import lombok.AllArgsConstructor;

/**
 * PurchaseStaff的UserDetails实现类
 */
@AllArgsConstructor
public class PurchaseStaffUserDetails extends BaseUserDetails<PurchaseStaffUser> {
    
    private PurchaseStaffUser purchaseStaffUser;
    
    private String role;
    
    @Override
    public PurchaseStaffUser getUser() {
        return purchaseStaffUser;
    }
    
    @Override
    public String getRole() {
        return role;
    }
    
}
