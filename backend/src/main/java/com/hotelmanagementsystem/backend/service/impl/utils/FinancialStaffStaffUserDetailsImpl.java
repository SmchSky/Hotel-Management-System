package com.hotelmanagementsystem.backend.service.impl.utils;

import com.hotelmanagementsystem.backend.pojo.FinancialStaff;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.SimpleTimeZone;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinancialStaffStaffUserDetailsImpl extends StaffUserDetailsImpl {

    private FinancialStaff financialStaff;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return financialStaff.getPassword();
    }

    @Override
    public String getUsername() {
        return financialStaff.getUsername();
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
        return financialStaff.getNumber();
    }

    @Override
    public String getName() {
        return financialStaff.getName();
    }

    @Override
    public String getPhone() {
        return financialStaff.getPhone();
    }
}
