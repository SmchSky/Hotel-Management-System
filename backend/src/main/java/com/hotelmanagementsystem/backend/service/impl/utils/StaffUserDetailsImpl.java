package com.hotelmanagementsystem.backend.service.impl.utils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


//此类为五类常规工作人员用户+超级用户共六类UserDetailsImpl类的父类，而OnlineUserDetailsImpl类则为直接应用UserDetails接口，原因是OnlineUser和StaffUser的信息细节不一样，故分开实现
public class StaffUserDetailsImpl implements UserDetails {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public String getNumber() {
        return null;
    }

    public String getName() {
        return null;
    }

    public String getPhone() {
        return null;
    }
}
