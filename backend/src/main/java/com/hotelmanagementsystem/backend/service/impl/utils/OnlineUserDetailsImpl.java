package com.hotelmanagementsystem.backend.service.impl.utils;

import com.hotelmanagementsystem.backend.pojo.OnlineUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


@Data
@NoArgsConstructor
@AllArgsConstructor
//UserDetailsImpl类的含义就是一个online_user所包含的一些细节信息
public class OnlineUserDetailsImpl implements UserDetails {

    private OnlineUser onlineUser;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return onlineUser.getPassword();
    }

    @Override
    public String getUsername() {
        return onlineUser.getUsername();
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
