package com.hotelmanagementsystem.backend.service.impl.online_user.account;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hotelmanagementsystem.backend.mapper.OnlineUserMapper;
import com.hotelmanagementsystem.backend.pojo.OnlineUser;
import com.hotelmanagementsystem.backend.service.inter.online_user.account.OnlineUserInfoUpdateService;
import com.hotelmanagementsystem.backend.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OnlineUserInfoUpdateServiceImpl implements OnlineUserInfoUpdateService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OnlineUserMapper onlineUserMapper;

    @Override
    public Map<String, String> update(Map<String, String> data) {
        //用于返回的map
        Map<String, String> map = new HashMap<>();

        //获取data中的信息
        String info = data.get("info");
        String info_confirmed = data.get("info_confirmed");
        String origin_username = data.get("origin_username");
        String type = data.get("type");

        //进行username合法性检验
        if ("username".equals(type)) {
            if (info.length() == 0) {
                map.put("error_message", "用户名不能为空！");
                return map;
            }
            if (info.indexOf(' ') >= 0) {
                map.put("error_message", "用户名不能包含空格！");
                return map;
            }
            if (info.length() > 20) {
                map.put("error_message", "用户名长度不能大于20！");
                return map;
            }
        }

        //进行password合法性检验
        if ("password".equals(type)) {
            if (info.length() == 0) {
                map.put("error_message", "密码不能为空！");
                return map;
            }
            if (info.length() > 20) {
                map.put("error_message", "密码长度不能大于20！");
                return map;
            }
            if (info_confirmed.length() == 0) {
                map.put("error_message", "确认密码不能为空！");
                return map;
            }

            if (!info.equals(info_confirmed)) {
                map.put("error_message", "两次输入的密码不一致！");
                return map;
            }
        }

        //进行phone合法性检验
        if ("phone".equals(type)) {
            if (info.length() != 11) {
                map.put("error_message", "手机号格式错误！");
                return map;
            }
        }

        //更新username
        if ("username".equals(type)) {
            //根据username在表中进行重复性检验
            QueryWrapper<OnlineUser> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("username", info);
            List<OnlineUser> list = onlineUserMapper.selectList(queryWrapper1);
            if (list.isEmpty()) {
                // 没有重复
                //在表中找到要更改的元组并更改
                UpdateWrapper<OnlineUser> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("username",origin_username).set("username", info);
                onlineUserMapper.update(null, updateWrapper);
                //利用username和duty生成新的token并返回（好！）
                String jwt = JwtUtil.createJWT(info);
                //返回map
                map.put("error_message", "success");
                map.put("token", jwt);
                return map;
            } else {
                // 有重复
                map.put("error_message", "用户名已存在！");
                return map;
            }
        }
        //更新password
        if ("password".equals(type)) {
            //在表中找到要更改的元组并更改
            UpdateWrapper<OnlineUser> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("username",origin_username).set("password", passwordEncoder.encode(info));
            onlineUserMapper.update(null, updateWrapper);
            //更新信息成功
            map.put("error_message", "success");
            return map;
        }
        //更新phone
        if ("phone".equals(type)) {
            //在表中找到要更改的元组并更改
            UpdateWrapper<OnlineUser> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("username",origin_username).set("phone", info);
            onlineUserMapper.update(null, updateWrapper);
            //更新信息成功
            map.put("error_message", "success");
            return map;
        }

        //更新的信息类型错误
        map.put("error_message", "更新的信息类型错误！");
        return map;
    }
}
