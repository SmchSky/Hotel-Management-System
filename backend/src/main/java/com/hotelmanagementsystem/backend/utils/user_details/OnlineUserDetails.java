package com.hotelmanagementsystem.backend.utils.user_details;

import com.hotelmanagementsystem.backend.pojo.OnlineUser;
import com.hotelmanagementsystem.backend.pojo.User;
import lombok.AllArgsConstructor;

/**
 * OnlineUser的UserDetails实现类
 */
@AllArgsConstructor
public class OnlineUserDetails extends BaseUserDetails<OnlineUser> {

    private OnlineUser onlineUser;
    private String role;
    
    @Override
    public OnlineUser getUser() {
        return onlineUser;
    }
    
    @Override
    public String getRole() {
        return role;
    }
}
