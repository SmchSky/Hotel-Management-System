package com.hotelmanagementsystem.backend.service.impl.administrator.account;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hotelmanagementsystem.backend.mapper.*;
import com.hotelmanagementsystem.backend.service.inter.administrator.account.StaffUserInfoUpdateService;
import com.hotelmanagementsystem.backend.utils.JwtUtil;
import com.hotelmanagementsystem.backend.utils.check.info_valid_check.UserInfoValidCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StaffUserInfoUpdateServiceImpl implements StaffUserInfoUpdateService {
    
    private final PasswordEncoder passwordEncoder;
    private final FinancialStaffMapper financialStaffMapper;
    private final FrontDeskStaffMapper frontDeskStaffMapper;
    private final HrStaffMapper hrStaffMapper;
    private final PurchaseStaffMapper purchaseStaffMapper;
    private final RestaurantStaffMapper restaurantStaffMapper;
    private final SuperuserMapper superuserMapper;
    
    @Autowired
    public StaffUserInfoUpdateServiceImpl(PasswordEncoder passwordEncoder, FinancialStaffMapper financialStaffMapper, FrontDeskStaffMapper frontDeskStaffMapper, HrStaffMapper hrStaffMapper, PurchaseStaffMapper purchaseStaffMapper, RestaurantStaffMapper restaurantStaffMapper, SuperuserMapper superuserMapper) {
        this.passwordEncoder = passwordEncoder;
        this.financialStaffMapper = financialStaffMapper;
        this.frontDeskStaffMapper = frontDeskStaffMapper;
        this.hrStaffMapper = hrStaffMapper;
        this.purchaseStaffMapper = purchaseStaffMapper;
        this.restaurantStaffMapper = restaurantStaffMapper;
        this.superuserMapper = superuserMapper;
    }
    
    @Override
    public Map<String, String> update(Map<String, String> data) {
        String new_info = data.get("info");
        String info_confirmed = data.get("info_confirmed");
        String duty = data.get("duty");
        // 员工编号，可以作为员工用户表的键，而且不能被用户主动修改，故传入用来找到表中要更新的原记录
        String number = data.get("number");
        String type = data.get("type");
        
        // 合法性检验的结果消息
        String message = null;
        // 合法性检验
        switch (type) {
            case "username" ->
                    message = UserInfoValidCheck.checkStaffUserInfoValid(new_info, null, null, null, null, null);
            case "password" ->
                    message = UserInfoValidCheck.checkStaffUserInfoValid(null, new_info, info_confirmed, null, null, null);
            case "phone" ->
                    message = UserInfoValidCheck.checkStaffUserInfoValid(null, null, null, new_info, null, null);
            case "name" ->
                    message = UserInfoValidCheck.checkStaffUserInfoValid(null, null, null, null, new_info, null);
        }
        // 信息不合法
        if (message != null && !message.equals("success")) {
            Map<String, String> map = new HashMap<>();
            map.put("message", message);
            return map;
        }
        // 根据职务类型，更新员工信息
        switch (duty) {
            case "财务管理员" -> {
                return updateStaffInfo(financialStaffMapper, type, new_info, number, "财务管理员");
            }
            case "酒店前台工作人员" -> {
                return updateStaffInfo(frontDeskStaffMapper, type, new_info, number, "酒店前台工作人员");
            }
            case "人事管理员" -> {
                return updateStaffInfo(hrStaffMapper, type, new_info, number, "人事管理员");
            }
            case "采购人员" -> {
                return updateStaffInfo(purchaseStaffMapper, type, new_info, number, "采购人员");
            }
            case "餐厅前台工作人员" -> {
                return updateStaffInfo(restaurantStaffMapper, type, new_info, number, "餐厅前台工作人员");
            }
            case "超级用户" -> {
                return updateStaffInfo(superuserMapper, type, new_info, number, "超级用户");
            }
            default -> {
                return new HashMap<>() {{
                    put("message", "职务类型错误！");
                }};
            }
        }
    }
    
    private <T> Map<String, String> updateStaffInfo(BaseMapper<T> mapper, String type, String newInfo, String number, String duty) {
        Map<String, String> map = new HashMap<>();
        switch (type) {
            case "username" -> {
                // 重复性检验
                QueryWrapper<T> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("username", newInfo);
                T repeatedUser = mapper.selectOne(queryWrapper);
                if (repeatedUser != null) {
                    map.put("message", "用户名已存在！");
                    return map;
                }
                // 执行更新操作
                UpdateWrapper<T> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("number", number).set("username", newInfo);
                mapper.update(null, updateWrapper);
                // 生成新的token
                String token = JwtUtil.createJwtTokenForStaffUser(newInfo, duty);
                map.put("message", "success");
                map.put("token", token);
            }
            case "password" -> {
                // 执行更新
                UpdateWrapper<T> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("number", number).set("password", passwordEncoder.encode(newInfo));
                mapper.update(null, updateWrapper);
                map.put("message", "success");
            }
            case "phone" -> {
                // 重复性检验
                QueryWrapper<T> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("phone", newInfo);
                T repeatedUser = mapper.selectOne(queryWrapper);
                if (repeatedUser != null) {
                    map.put("message", "手机号已存在！");
                    return map;
                }
                // 执行更新操作
                UpdateWrapper<T> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("number", number).set("phone", newInfo);
                mapper.update(null, updateWrapper);
                map.put("message", "success");
            }
            case "name" -> {
                // 执行更新
                UpdateWrapper<T> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("number", number).set("name", newInfo);
                mapper.update(null, updateWrapper);
                map.put("message", "success");
            }
            default -> map.put("message", "更新的信息类型错误！");
        }
        return map;
    }
}