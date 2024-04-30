package com.hotelmanagementsystem.backend.service.impl.utils;

import com.hotelmanagementsystem.backend.pojo.PurchaseStaff;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseStaffStaffUserDetailsImpl extends StaffUserDetailsImpl {
    private PurchaseStaff purchaseStaff;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return purchaseStaff.getPassword();
    }

    @Override
    public String getUsername() {
        return purchaseStaff.getUsername();
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
        return purchaseStaff.getNumber();
    }

    @Override
    public String getName() {
        return purchaseStaff.getName();
    }

    @Override
    public String getPhone() {
        return purchaseStaff.getPhone();
    }
}
