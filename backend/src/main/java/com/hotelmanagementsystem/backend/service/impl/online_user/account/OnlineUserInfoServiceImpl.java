package com.hotelmanagementsystem.backend.service.impl.online_user.account;

import com.hotelmanagementsystem.backend.pojo.OnlineUser;
import com.hotelmanagementsystem.backend.service.impl.utils.OnlineUserDetailsImpl;
import com.hotelmanagementsystem.backend.service.inter.online_user.account.OnlineUserInfoService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OnlineUserInfoServiceImpl implements OnlineUserInfoService {
    @Override
    public Map<String, String> getInfo() {
        // authenticationToken来表示当前访问者的信息
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        // 获取当事人信息对象loginUserDetails，本质为UserDetailsImpl类的一个实例
        OnlineUserDetailsImpl loginUserDetails = (OnlineUserDetailsImpl) authenticationToken.getPrincipal();
        // 获取当前已登录的用户
        OnlineUser loginuser = loginUserDetails.getOnlineUser();

        // 返回当前已登录用户的信息
        Map<String, String> map = new HashMap<>();
        map.put("error_message", "success");
        map.put("username", loginuser.getUsername());
        map.put("phone", loginuser.getPhone());
        return map;
    }
}
