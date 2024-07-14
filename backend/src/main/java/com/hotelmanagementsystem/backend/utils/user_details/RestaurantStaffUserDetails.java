package com.hotelmanagementsystem.backend.utils.user_details;

import com.hotelmanagementsystem.backend.pojo.RestaurantStaffUser;
import com.hotelmanagementsystem.backend.pojo.User;
import lombok.AllArgsConstructor;

/**
 * RestaurantStaff的UserDetails实现类
 */
@AllArgsConstructor
public class RestaurantStaffUserDetails extends BaseUserDetails<RestaurantStaffUser> {

    private RestaurantStaffUser restaurantStaffUser;
    
    private String role;
    
    @Override
    public RestaurantStaffUser getUser() {
        return restaurantStaffUser;
    }
    
    @Override
    public String getRole() {
        return role;
    }
}
