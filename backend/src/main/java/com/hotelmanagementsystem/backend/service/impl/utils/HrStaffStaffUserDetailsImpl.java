package com.hotelmanagementsystem.backend.service.impl.utils;

import com.hotelmanagementsystem.backend.pojo.HrStaff;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HrStaffStaffUserDetailsImpl extends StaffUserDetailsImpl {

    private HrStaff hrStaff;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return hrStaff.getPassword();
    }

    @Override
    public String getUsername() {
        return hrStaff.getUsername();
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
        return hrStaff.getNumber();
    }

    @Override
    public String getName() {
        return hrStaff.getName();
    }

    @Override
    public String getPhone() {
        return hrStaff.getPhone();
    }
}
