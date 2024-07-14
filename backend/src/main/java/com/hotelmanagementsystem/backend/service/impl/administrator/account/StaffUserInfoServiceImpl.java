package com.hotelmanagementsystem.backend.service.impl.administrator.account;

import com.hotelmanagementsystem.backend.pojo.User;
import com.hotelmanagementsystem.backend.utils.user_details.BaseUserDetails;
import com.hotelmanagementsystem.backend.service.inter.administrator.account.StaffUserInfoService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Service
public class StaffUserInfoServiceImpl implements StaffUserInfoService {
    @Override
    public Map<String, String> getUserInfo() {
        User user = ((BaseUserDetails<?>) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        // 返回当前已登录用户的信息
        Map<String, String> map = new HashMap<>();
        map.put("message", "success");
        map.put("username", user.getUsername());
        map.put("phone", user.getPhone());
        // 使用反射来调用具体的子类中的方法
        try {
            Method method_getNumber = user.getClass().getMethod("getNumber");
            String number = (String) method_getNumber.invoke(user);
            Method method_getName = user.getClass().getMethod("getName");
            String name = (String) method_getName.invoke(user);
            Method method_getDuty = user.getClass().getMethod("getDuty");
            String duty = (String) method_getDuty.invoke(user);
            map.put("number", number);
            map.put("name", name);
            map.put("duty", duty);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return map;
    }
}
