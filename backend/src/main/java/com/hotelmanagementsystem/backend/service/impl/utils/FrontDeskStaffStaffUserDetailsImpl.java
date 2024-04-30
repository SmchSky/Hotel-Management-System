package com.hotelmanagementsystem.backend.service.impl.utils;

import com.hotelmanagementsystem.backend.pojo.FrontDeskStaff;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FrontDeskStaffStaffUserDetailsImpl extends StaffUserDetailsImpl {
    private FrontDeskStaff frontDeskStaff;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return frontDeskStaff.getPassword();
    }

    @Override
    public String getUsername() {
        return frontDeskStaff.getUsername();
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

    @Override
    public String getNumber() {
        return frontDeskStaff.getNumber();
    }

    @Override
    public String getName() {
        return frontDeskStaff.getName();
    }

    @Override
    public String getPhone() {
        return frontDeskStaff.getPhone();
    }
}
