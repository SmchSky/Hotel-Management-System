package com.hotelmanagementsystem.backend.service.impl.administrator.account;

import com.hotelmanagementsystem.backend.service.impl.utils.StaffUserDetailsImpl;
import com.hotelmanagementsystem.backend.service.inter.administrator.account.StaffUserLoginService;
import com.hotelmanagementsystem.backend.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
public class StaffUserLoginServiceImpl implements StaffUserLoginService {
    @Autowired
    private HttpServletRequest request;  //用于获取AJAX请求中的参数
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Map<String, String> getToken(String username, String password) {
        // authentication此时表示一个要通过用户名、密码进行认证的认证请求
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);

        // 对authentication重新赋值，通过authenticationManager成功认证后，此时其表示一个认证信息；如果认证失败，spring会自动处理（报异常）
        authentication = authenticationManager.authenticate(authentication);

        // 将认证信息存储在 SecurityContextHolder中，可在应用程序中获取当前的认证信息
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 获取当事人信息对象
        StaffUserDetailsImpl loginUserDetails = (StaffUserDetailsImpl) authentication.getPrincipal();

        //// 获取认证成功后（已登录）的用户
        //OnlineUser login_user = loginUserDetails.getOnlineUser();

        //获取用户职务信息
        String duty = request.getParameter("duty");

        // 利用用户的用户名和职务信息生成JWT token
        String jwt = JwtUtil.createJWT(loginUserDetails.getUsername(),duty);

        // 返回token
        Map<String, String> map = new HashMap<>();
        map.put("error_message", "success");
        map.put("token", jwt);
        return map;
    }
}
