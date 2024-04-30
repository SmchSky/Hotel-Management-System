package com.hotelmanagementsystem.backend.service.impl.online_user.account;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hotelmanagementsystem.backend.mapper.OnlineUserMapper;
import com.hotelmanagementsystem.backend.pojo.OnlineUser;
import com.hotelmanagementsystem.backend.service.inter.online_user.account.OnlineUserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RegisterServiceImpl implements OnlineUserRegisterService {

    @Autowired
    private OnlineUserMapper onlineUserMapper;  //因为要访问数据库查询用户名是否有重复

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    //利用前端用户输入的信息完成注册操作，并在数据库的表中进行元组插入
    public Map<String, String> register(String username, String password, String confirmed_password, String phone) {
        Map<String, String> map = new HashMap<>();

        if (username.length() == 0) {
            map.put("error_message", "用户名不能为空！");
            return map;
        }

        if (password.length() == 0) {
            map.put("error_message", "密码不能为空！");
            return map;
        }

        if (confirmed_password.length() == 0) {
            map.put("error_message", "确认密码不能为空！");
            return map;
        }

        if (!password.equals(confirmed_password)) {
            map.put("error_message", "两次输入的密码不一致！");
            return map;
        }

        ////将用户名中的空白字符去掉(包括空格，tab，回车)
        //username = username.trim();

        //如果username中包含空格
        if (username.indexOf(' ') >= 0) {
            map.put("error_message", "用户名不能包含空格！");
            return map;
        }

        if (username.length() > 20) {
            map.put("error_message", "用户名长度不能大于20！");
            return map;
        }

        if (password.length() > 20) {
            map.put("error_message", "密码长度不能大于20！");
            return map;
        }

        if (phone.length() != 11) {
            map.put("error_message", "手机号格式错误！");
            return map;
        }

        //查询online_user表中"username"等于username的元组
        QueryWrapper<OnlineUser> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("username", username);
        List<OnlineUser> users = onlineUserMapper.selectList(queryWrapper1);
        if (!users.isEmpty()) {
            map.put("error_message", "用户名已存在！");
            return map;
        }

        //查询online_user表中"phone"等于phone的元组
        QueryWrapper<OnlineUser> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("phone", phone);
        users = onlineUserMapper.selectList(queryWrapper2);
        if (!users.isEmpty()) {
            map.put("error_message", "手机号已存在！");
            return map;
        }

        //对密码进行加密
        String encodedPassword = passwordEncoder.encode(password);

        //新建一个OnlineUser对象
        OnlineUser user = new OnlineUser(username, encodedPassword, phone);
        //在online_user表中插入新的元组
        onlineUserMapper.insert(user);

        map.put("error_message", "注册成功！");
        return map;
    }

}
