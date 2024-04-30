package com.hotelmanagementsystem.backend.service.impl.administrator.account;

import com.hotelmanagementsystem.backend.service.impl.utils.StaffUserDetailsImpl;
import com.hotelmanagementsystem.backend.service.inter.administrator.account.StaffUserInfoService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StaffUserInfoServiceImpl implements StaffUserInfoService {
    @Override
    public Map<String, String> getInfo() {
        // authenticationToken来表示当前访问者的信息
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        // 获取当事人信息对象loginUserDetails
        StaffUserDetailsImpl loginUserDetails = (StaffUserDetailsImpl) authenticationToken.getPrincipal();

        // 返回当前已登录用户的信息
        Map<String, String> map = new HashMap<>();
        map.put("error_message", "success");
        map.put("username", loginUserDetails.getUsername());
        map.put("number", loginUserDetails.getNumber());
        map.put("name", loginUserDetails.getName());
        map.put("phone", loginUserDetails.getPhone());
        return map;
    }
}
