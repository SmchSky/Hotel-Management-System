package com.hotelmanagementsystem.backend.utils.user_details;

import com.hotelmanagementsystem.backend.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


/**
 * 具体类型的UserDetails类的抽象基类
 */
public abstract class BaseUserDetails<T extends User> implements UserDetails {
    
    public abstract T getUser();
    
    public abstract String getRole();
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
    
    @Override
    public String getUsername() {
        return getUser().getUsername();
    }
    
    @Override
    public String getPassword() {
        return getUser().getPassword();
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        return true;
    }
    
}
