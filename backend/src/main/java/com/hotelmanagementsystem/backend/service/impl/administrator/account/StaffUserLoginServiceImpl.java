package com.hotelmanagementsystem.backend.service.impl.administrator.account;

import com.hotelmanagementsystem.backend.utils.user_details.BaseUserDetails;
import com.hotelmanagementsystem.backend.service.inter.administrator.account.StaffUserLoginService;
import com.hotelmanagementsystem.backend.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StaffUserLoginServiceImpl implements StaffUserLoginService {
    
    private final AuthenticationManager authenticationManager;
    
    @Autowired
    public StaffUserLoginServiceImpl(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    
    @Override
    public Map<String, String> login(String username, String password, String duty) {
        // 新建认证请求
        UsernamePasswordAuthenticationToken authenticationRequest = new UsernamePasswordAuthenticationToken(username, password);
        // 进行认证
        try {
            Authentication authentication = authenticationManager.authenticate(authenticationRequest);
            if (authentication.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
                BaseUserDetails<?> loginUserDetails = (BaseUserDetails<?>) authentication.getPrincipal();
                // 使用用户名和职务信息生成JWT
                String token = JwtUtil.createJwtTokenForStaffUser(loginUserDetails.getUsername(), duty);
                return new HashMap<>() {{
                    put("message", "success");
                    put("token", token);
                }};
            } else {
                return new HashMap<>() {{
                    put("message", "error");
                }};
            }
        } catch (Exception e) {
            return new HashMap<>() {{
                put("message", "error");
            }};
        }
    }
}
