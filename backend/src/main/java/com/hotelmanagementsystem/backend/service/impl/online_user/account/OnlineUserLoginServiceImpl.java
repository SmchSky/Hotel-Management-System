package com.hotelmanagementsystem.backend.service.impl.online_user.account;

import com.hotelmanagementsystem.backend.pojo.OnlineUser;
import com.hotelmanagementsystem.backend.utils.user_details.OnlineUserDetails;
import com.hotelmanagementsystem.backend.service.inter.online_user.account.OnlineUserLoginService;
import com.hotelmanagementsystem.backend.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OnlineUserLoginServiceImpl implements OnlineUserLoginService {
    
    private final AuthenticationManager authenticationManager;
    
    @Autowired
    public OnlineUserLoginServiceImpl(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    
    /**
     * 对用户名和密码进行验证并返回token
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录失败信息或者成功信息（包含token）
     */
    @Override
    public Map<String, String> login(String username, String password) {
        // 创建认证请求对象
        UsernamePasswordAuthenticationToken authenticationRequest = new UsernamePasswordAuthenticationToken(username, password);
        // 进行认证
        try {
            /*
              使用authenticationManager的authenticate方法进行认证，并返回一个认证信息，具体的认证逻辑如下：
              AuthenticationManager接收到认证请求对象之后，首先会将认证请求委托给一个或多个AuthenticationProvider对象，它会调用UserDetailsService服务，而UserDetailsService会根据传入的用户名从数据库中加载用户信息，并返回一个UserDetails对象，最后AuthenticationProvider对象会将传入的凭证（密码）与UserDetails中的密码进行比较。如果密码匹配，则认证成功，否则认证失败。
             */
            Authentication authentication = authenticationManager.authenticate(authenticationRequest);
            // 如果认证通过
            if (authentication.isAuthenticated()) {
                // 将认证信息存储在安全上下文中，方便在应用程序中获取当前的认证信息
                SecurityContextHolder.getContext().setAuthentication(authentication);
                // 获取当事人信息对象loginUserDetails
                OnlineUserDetails userDetails = (OnlineUserDetails) authentication.getPrincipal();
                // 获取认证成功后的用户
                OnlineUser onlineUser = userDetails.getUser();
                // 利用用户的用户名生成JWT token
                String token = JwtUtil.createJwtTokenForOnlineUser(onlineUser.getUsername());
                // 返回token
                return new HashMap<>() {{
                    put("message", "success");
                    put("token", token);
                }};
            } else {
                // 认证失败
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
