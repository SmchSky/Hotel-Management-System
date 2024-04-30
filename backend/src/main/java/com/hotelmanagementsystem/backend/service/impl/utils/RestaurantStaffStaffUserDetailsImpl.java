package com.hotelmanagementsystem.backend.service.impl.utils;

import com.hotelmanagementsystem.backend.pojo.RestaurantStaff;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantStaffStaffUserDetailsImpl extends StaffUserDetailsImpl {

    private RestaurantStaff restaurantStaff;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return restaurantStaff.getPassword();
    }

    @Override
    public String getUsername() {
        return restaurantStaff.getUsername();
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
        return restaurantStaff.getNumber();
    }

    @Override
    public String getName() {
        return restaurantStaff.getName();
    }

    @Override
    public String getPhone() {
        return restaurantStaff.getPhone();
    }
}
