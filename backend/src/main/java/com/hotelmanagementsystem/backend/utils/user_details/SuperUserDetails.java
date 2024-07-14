package com.hotelmanagementsystem.backend.utils.user_details;

import com.hotelmanagementsystem.backend.pojo.Superuser;
import com.hotelmanagementsystem.backend.pojo.User;
import lombok.AllArgsConstructor;

/**
 * Superuser的UserDetails实现类
 */
@AllArgsConstructor
public class SuperUserDetails extends BaseUserDetails<Superuser> {
    
    private Superuser superuser;
    
    private String role;
    
    @Override
    public Superuser getUser() {
        return superuser;
    }
    
    @Override
    public String getRole() {
        return role;
    }
}
