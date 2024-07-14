package com.hotelmanagementsystem.backend.service.impl.online_user.account;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hotelmanagementsystem.backend.mapper.OnlineUserMapper;
import com.hotelmanagementsystem.backend.pojo.OnlineUser;
import com.hotelmanagementsystem.backend.service.inter.online_user.account.OnlineUserInfoUpdateService;
import com.hotelmanagementsystem.backend.utils.JwtUtil;
import com.hotelmanagementsystem.backend.utils.check.info_valid_check.UserInfoValidCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OnlineUserInfoUpdateServiceImpl implements OnlineUserInfoUpdateService {
    
    private final PasswordEncoder passwordEncoder;
    private final OnlineUserMapper onlineUserMapper;
    
    @Autowired
    public OnlineUserInfoUpdateServiceImpl(PasswordEncoder passwordEncoder, OnlineUserMapper onlineUserMapper) {
        this.passwordEncoder = passwordEncoder;
        this.onlineUserMapper = onlineUserMapper;
    }
    
    @Override
    public Map<String, String> updateUserInfo(Map<String, String> data) {
        Map<String, String> map = new HashMap<>();
        String new_info = data.get("new_info");
        String info_confirmed = data.get("info_confirmed");
        String origin_username = data.get("origin_username");
        String type = data.get("type");
        // 参数完整性检查
        if (new_info == null || origin_username == null || type == null) {
            map.put("message", "信息不全！");
            return map;
        }
        // 合法性检验
        String message = validateInfo(type, new_info, info_confirmed);
        if (!"success".equals(message)) {
            map.put("message", message);
            return map;
        }
        // 根据类型执行更新操作
        updateInfo(map, origin_username, new_info, type);
        return map;
    }
    
    /**
     * 根据type进行信息合法性检验
     *
     * @param type           信息类型
     * @param new_info       新信息
     * @param info_confirmed 确认的新信息
     * @return 要检查的字段合法，返回success，否则返回不合法的原因
     */
    private String validateInfo(String type, String new_info, String info_confirmed) {
        return switch (type) {
            case "username" -> UserInfoValidCheck.checkOnlineUserInfoValid(new_info, null, null, null);
            case "password" -> UserInfoValidCheck.checkOnlineUserInfoValid(null, new_info, info_confirmed, null);
            case "phone" -> UserInfoValidCheck.checkOnlineUserInfoValid(null, null, null, new_info);
            default -> "更新的信息类型错误！";
        };
    }
    
    /**
     * 更新信息
     *
     * @param map             要返回的map
     * @param origin_username 原用户名
     * @param new_info        新信息
     * @param field           更新的字段类型
     */
    private void updateInfo(Map<String, String> map, String origin_username, String new_info, String field) {
        switch (field) {
            // 需要进行重复性检验的字段
            case "username", "phone" -> {
                if (checkRepeated(field, new_info)) {
                    // 有重复
                    map.put("message", field.equals("username") ? "用户名已存在！" : "手机号已存在！");
                } else {
                    // 没有重复，执行更新操作
                    UpdateWrapper<OnlineUser> updateWrapper = new UpdateWrapper<>();
                    updateWrapper.eq("username", origin_username).set(field, new_info);
                    onlineUserMapper.update(null, updateWrapper);
                    // 返回success
                    map.put("message", "success");
                    // 如果更新的是用户名，则需要生成一个新的token并返回
                    if (field.equals("username")) {
                        map.put("token", JwtUtil.createJwtTokenForOnlineUser(new_info));
                    }
                }
            }
            // 无需进行重复性检验的字段
            case "password" -> {
                // 直接执行更新操作
                UpdateWrapper<OnlineUser> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("username", origin_username).set(field, passwordEncoder.encode(new_info));
                onlineUserMapper.update(null, updateWrapper);
                map.put("message", "success");
            }
            default -> map.put("message", "更新的信息类型错误！");
        }
    }
    
    /**
     * 对online_user表中field字段在new_info值下进行重复性检查
     *
     * @param field    被检查的字段
     * @param new_info 被检查的值
     * @return 无重复返回false，有重复返回true
     */
    private boolean checkRepeated(String field, String new_info) {
        QueryWrapper<OnlineUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(field, new_info);
        return onlineUserMapper.selectOne(queryWrapper) != null;
    }
}
