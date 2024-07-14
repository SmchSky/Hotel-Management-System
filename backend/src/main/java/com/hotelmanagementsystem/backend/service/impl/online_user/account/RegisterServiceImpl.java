package com.hotelmanagementsystem.backend.service.impl.online_user.account;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hotelmanagementsystem.backend.mapper.OnlineUserMapper;
import com.hotelmanagementsystem.backend.pojo.OnlineUser;
import com.hotelmanagementsystem.backend.service.inter.online_user.account.OnlineUserRegisterService;
import com.hotelmanagementsystem.backend.utils.check.info_valid_check.UserInfoValidCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RegisterServiceImpl implements OnlineUserRegisterService {
    
    private final OnlineUserMapper onlineUserMapper;
    
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public RegisterServiceImpl(OnlineUserMapper onlineUserMapper, PasswordEncoder passwordEncoder) {
        this.onlineUserMapper = onlineUserMapper;
        this.passwordEncoder = passwordEncoder;
    }
    
    /**
     * 完成线上用户注册，并将信息存入数据库中
     *
     * @param username           用户名
     * @param password           密码
     * @param confirmed_password 确认密码
     * @param phone              手机号
     * @return 注册结果信息
     */
    @Override
    public Map<String, String> register(String username, String password, String confirmed_password, String phone) {
        Map<String, String> map = new HashMap<>();
        
        // 参数完整性检查
        if(username == null || password == null || confirmed_password == null || phone == null){
            map.put("message", "信息不全！");
            return map;
        }
        
        // 信息合法性检验
        String message = UserInfoValidCheck.checkOnlineUserInfoValid(username, password, confirmed_password, phone);
        if (!message.equals("success")) {
            map.put("message", message);
            return map;
        }
        
        // 用户名重复性检验
        QueryWrapper<OnlineUser> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("username", username);
        OnlineUser repeated_user = onlineUserMapper.selectOne(queryWrapper1);
        if (repeated_user != null) {
            map.put("message", "用户名已存在！");
            return map;
        }
        
        // 手机号重复性检查
        QueryWrapper<OnlineUser> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("phone", phone);
        repeated_user = onlineUserMapper.selectOne(queryWrapper2);
        if (repeated_user != null) {
            map.put("message", "手机号已存在！");
            return map;
        }
        
        // 对密码进行加密
        String encodedPassword = passwordEncoder.encode(password);
        
        // 新建OnlineUser对象
        OnlineUser new_user = new OnlineUser(username, encodedPassword, phone);
        // 在online_user表中插入新的元组
        onlineUserMapper.insert(new_user);
        
        map.put("message", "注册成功！");
        return map;
    }
    
}
