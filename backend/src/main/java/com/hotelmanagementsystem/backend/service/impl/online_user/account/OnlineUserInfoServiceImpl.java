package com.hotelmanagementsystem.backend.service.impl.online_user.account;

import com.hotelmanagementsystem.backend.pojo.OnlineUser;
import com.hotelmanagementsystem.backend.pojo.User;
import com.hotelmanagementsystem.backend.utils.user_details.BaseUserDetails;
import com.hotelmanagementsystem.backend.utils.user_details.OnlineUserDetails;
import com.hotelmanagementsystem.backend.service.inter.online_user.account.OnlineUserInfoService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OnlineUserInfoServiceImpl implements OnlineUserInfoService {
    @Override
    public Map<String, String> getUserInfo() {
        OnlineUser user = ((OnlineUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        // 返回当前已登录用户的信息
        Map<String, String> map = new HashMap<>();
        map.put("message", "success");
        map.put("username", user.getUsername());
        map.put("phone", user.getPhone());
        return map;
    }
}
