package com.hotelmanagementsystem.backend.service.impl.administrator.account;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hotelmanagementsystem.backend.mapper.*;
import com.hotelmanagementsystem.backend.pojo.*;
import com.hotelmanagementsystem.backend.service.inter.administrator.account.StaffUserInfoUpdateService;
import com.hotelmanagementsystem.backend.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StaffUserInfoUpdateServiceImpl implements StaffUserInfoUpdateService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private FinancialStaffMapper financialStaffMapper;
    @Autowired
    private FrontDeskStaffMapper frontDeskStaffMapper;
    @Autowired
    private HrStaffMapper hrStaffMapper;
    @Autowired
    private PurchaseStaffMapper purchaseStaffMapper;
    @Autowired
    private RestaurantStaffMapper restaurantStaffMapper;
    @Autowired
    private SuperuserMapper superuserMapper;

    @Override
    public Map<String, String> update(Map<String, String> data) {

        //用于返回的map
        Map<String, String> map = new HashMap<>();

        //获取data中的信息
        String info = data.get("info");
        String info_confirmed = data.get("info_confirmed");
        String duty = data.get("duty");
        String number = data.get("number");
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

        //进行name合法性检验
        if ("name".equals(type)) {
            if (info.length() == 0) {
                map.put("error_message", "员工姓名不能为空！");
                return map;
            }
            if (info.length() > 20) {
                map.put("error_message", "员工姓名长度不能大于20！");
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

        //根据duty的情况在不同表中进行查找是否重复以及更新
        //酒店前台工作人员
        if ("酒店前台工作人员".equals(duty)) {
            if ("username".equals(type)) {
                //根据username在表中进行重复性检验
                QueryWrapper<FrontDeskStaff> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("username", info);
                List<FrontDeskStaff> list = frontDeskStaffMapper.selectList(queryWrapper1);
                if (list.isEmpty()) {
                    //没有重复
                    //在表中找到要更改的元组并更改
                    UpdateWrapper<FrontDeskStaff> updateWrapper = new UpdateWrapper<>();
                    updateWrapper.eq("number", number).set("username", info);
                    frontDeskStaffMapper.update(null, updateWrapper);
                    //利用username和duty生成新的token并返回（好！）
                    String jwt = JwtUtil.createJWT(info, duty);
                    //返回map
                    map.put("error_message", "success");
                    map.put("token", jwt);
                    return map;
                } else {
                    //有重复
                    map.put("error_message", "用户名已存在！");
                    return map;
                }
            }
            if ("password".equals(type)) {
                //在表中找到要更改的元组并更改
                UpdateWrapper<FrontDeskStaff> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("number", number).set("password", passwordEncoder.encode(info));
                frontDeskStaffMapper.update(null, updateWrapper);
                //更新信息成功
                map.put("error_message", "success");
                return map;
            }
            if ("name".equals(type)) {
                //在表中找到要更改的元组并更改
                UpdateWrapper<FrontDeskStaff> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("number", number).set("name", info);
                frontDeskStaffMapper.update(null, updateWrapper);
                //更新信息成功
                map.put("error_message", "success");
                return map;
            }
            if ("phone".equals(type)) {
                //在表中找到要更改的元组并更改
                UpdateWrapper<FrontDeskStaff> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("number", number).set("phone", info);
                frontDeskStaffMapper.update(null, updateWrapper);
                //更新信息成功
                map.put("error_message", "success");
                return map;
            }
        }

        //餐厅前台工作人员
        if ("餐厅前台工作人员".equals(duty)) {
            if ("username".equals(type)) {
                //根据username在表中进行重复性检验
                QueryWrapper<RestaurantStaff> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("username", info);
                List<RestaurantStaff> list = restaurantStaffMapper.selectList(queryWrapper1);
                if (list.isEmpty()) {
                    //没有重复
                    //在表中找到要更改的元组并更改
                    UpdateWrapper<RestaurantStaff> updateWrapper = new UpdateWrapper<>();
                    updateWrapper.eq("number", number).set("username", info);
                    restaurantStaffMapper.update(null, updateWrapper);
                    //利用username和duty生成新的token并返回（好！）
                    String jwt = JwtUtil.createJWT(info, duty);
                    //返回map
                    map.put("error_message", "success");
                    map.put("token", jwt);
                    return map;
                } else {
                    //有重复
                    map.put("error_message", "用户名已存在！");
                    return map;
                }
            }
            if ("password".equals(type)) {
                //在表中找到要更改的元组并更改
                UpdateWrapper<RestaurantStaff> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("number", number).set("password", passwordEncoder.encode(info));
                restaurantStaffMapper.update(null, updateWrapper);
                //更新信息成功
                map.put("error_message", "success");
                return map;
            }
            if ("name".equals(type)) {
                //在表中找到要更改的元组并更改
                UpdateWrapper<RestaurantStaff> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("number", number).set("name", info);
                restaurantStaffMapper.update(null, updateWrapper);
                //更新信息成功
                map.put("error_message", "success");
                return map;
            }
            if ("phone".equals(type)) {
                //在表中找到要更改的元组并更改
                UpdateWrapper<RestaurantStaff> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("number", number).set("phone", info);
                restaurantStaffMapper.update(null, updateWrapper);
                //更新信息成功
                map.put("error_message", "success");
                return map;
            }
        }

        //人事管理员
        if ("人事管理员".equals(duty)) {
            if ("username".equals(type)) {
                // 根据username在表中进行重复性检验
                QueryWrapper<HrStaff> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("username", info);
                List<HrStaff> list = hrStaffMapper.selectList(queryWrapper1);
                if (list.isEmpty()) {
                    // 没有重复
                    // 在表中找到要更改的元组并更改
                    UpdateWrapper<HrStaff> updateWrapper = new UpdateWrapper<>();
                    updateWrapper.eq("number", number).set("username", info);
                    hrStaffMapper.update(null, updateWrapper);
                    // 利用username和duty生成新的token并返回
                    String jwt = JwtUtil.createJWT(info, duty);
                    // 返回map
                    map.put("error_message", "success");
                    map.put("token", jwt);
                    return map;
                } else {
                    // 有重复
                    map.put("error_message", "用户名已存在！");
                    return map;
                }
            }
            if ("password".equals(type)) {
                // 在表中找到要更改的元组并更改
                UpdateWrapper<HrStaff> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("number", number).set("password", passwordEncoder.encode(info));
                hrStaffMapper.update(null, updateWrapper);
                // 更新信息成功
                map.put("error_message", "success");
                return map;
            }
            if ("name".equals(type)) {
                // 在表中找到要更改的元组并更改
                UpdateWrapper<HrStaff> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("number", number).set("name", info);
                hrStaffMapper.update(null, updateWrapper);
                // 更新信息成功
                map.put("error_message", "success");
                return map;
            }
            if ("phone".equals(type)) {
                // 在表中找到要更改的元组并更改
                UpdateWrapper<HrStaff> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("number", number).set("phone", info);
                hrStaffMapper.update(null, updateWrapper);
                // 更新信息成功
                map.put("error_message", "success");
                return map;
            }
        }

        //财务管理员
        if ("财务管理员".equals(duty)) {
            if ("username".equals(type)) {
                // 根据username在表中进行重复性检验
                QueryWrapper<FinancialStaff> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("username", info);
                List<FinancialStaff> list = financialStaffMapper.selectList(queryWrapper1);
                if (list.isEmpty()) {
                    // 没有重复
                    // 在表中找到要更改的元组并更改
                    UpdateWrapper<FinancialStaff> updateWrapper = new UpdateWrapper<>();
                    updateWrapper.eq("number", number).set("username", info);
                    financialStaffMapper.update(null, updateWrapper);
                    // 利用username和duty生成新的token并返回
                    String jwt = JwtUtil.createJWT(info, duty);
                    // 返回map
                    map.put("error_message", "success");
                    map.put("token", jwt);
                    return map;
                } else {
                    // 有重复
                    map.put("error_message", "用户名已存在！");
                    return map;
                }
            }
            if ("password".equals(type)) {
                // 在表中找到要更改的元组并更改
                UpdateWrapper<FinancialStaff> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("number", number).set("password", passwordEncoder.encode(info));
                financialStaffMapper.update(null, updateWrapper);
                // 更新信息成功
                map.put("error_message", "success");
                return map;
            }
            if ("name".equals(type)) {
                // 在表中找到要更改的元组并更改
                UpdateWrapper<FinancialStaff> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("number", number).set("name", info);
                financialStaffMapper.update(null, updateWrapper);
                // 更新信息成功
                map.put("error_message", "success");
                return map;
            }
            if ("phone".equals(type)) {
                // 在表中找到要更改的元组并更改
                UpdateWrapper<FinancialStaff> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("number", number).set("phone", info);
                financialStaffMapper.update(null, updateWrapper);
                // 更新信息成功
                map.put("error_message", "success");
                return map;
            }
        }

        //采购人员
        if ("采购人员".equals(duty)) {
            if ("username".equals(type)) {
                // 根据username在表中进行重复性检验
                QueryWrapper<PurchaseStaff> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("username", info);
                List<PurchaseStaff> list = purchaseStaffMapper.selectList(queryWrapper1);
                if (list.isEmpty()) {
                    // 没有重复
                    // 在表中找到要更改的元组并更改
                    UpdateWrapper<PurchaseStaff> updateWrapper = new UpdateWrapper<>();
                    updateWrapper.eq("number", number).set("username", info);
                    purchaseStaffMapper.update(null, updateWrapper);
                    // 利用username和duty生成新的token并返回（好！）
                    String jwt = JwtUtil.createJWT(info, duty);
                    // 返回map
                    map.put("error_message", "success");
                    map.put("token", jwt);
                    return map;
                } else {
                    // 有重复
                    map.put("error_message", "用户名已存在！");
                    return map;
                }
            }
            if ("password".equals(type)) {
                // 在表中找到要更改的元组并更改
                UpdateWrapper<PurchaseStaff> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("number", number).set("password", passwordEncoder.encode(info));
                purchaseStaffMapper.update(null, updateWrapper);
                // 更新信息成功
                map.put("error_message", "success");
                return map;
            }
            if ("name".equals(type)) {
                // 在表中找到要更改的元组并更改
                UpdateWrapper<PurchaseStaff> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("number", number).set("name", info);
                purchaseStaffMapper.update(null, updateWrapper);
                // 更新信息成功
                map.put("error_message", "success");
                return map;
            }
            if ("phone".equals(type)) {
                // 在表中找到要更改的元组并更改
                UpdateWrapper<PurchaseStaff> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("number", number).set("phone", info);
                purchaseStaffMapper.update(null, updateWrapper);
                // 更新信息成功
                map.put("error_message", "success");
                return map;
            }
        }

        //超级用户
        if ("超级用户".equals(duty)) {
            if ("username".equals(type)) {
                // 根据username在表中进行重复性检验
                QueryWrapper<Superuser> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("username", info);
                List<Superuser> list = superuserMapper.selectList(queryWrapper1);
                if (list.isEmpty()) {
                    // 没有重复
                    // 在表中找到要更改的元组并更改
                    UpdateWrapper<Superuser> updateWrapper = new UpdateWrapper<>();
                    updateWrapper.eq("number", number).set("username", info);
                    superuserMapper.update(null, updateWrapper);
                    // 利用username和duty生成新的token并返回（好！）
                    String jwt = JwtUtil.createJWT(info, duty);
                    // 返回map
                    map.put("error_message", "success");
                    map.put("token", jwt);
                    return map;
                } else {
                    // 有重复
                    map.put("error_message", "用户名已存在！");
                    return map;
                }
            }
            if ("password".equals(type)) {
                // 在表中找到要更改的元组并更改
                UpdateWrapper<Superuser> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("number", number).set("password", passwordEncoder.encode(info));
                superuserMapper.update(null, updateWrapper);
                // 更新信息成功
                map.put("error_message", "success");
                return map;
            }
            if ("name".equals(type)) {
                // 在表中找到要更改的元组并更改
                UpdateWrapper<Superuser> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("number", number).set("name", info);
                superuserMapper.update(null, updateWrapper);
                // 更新信息成功
                map.put("error_message", "success");
                return map;
            }
            if ("phone".equals(type)) {
                // 在表中找到要更改的元组并更改
                UpdateWrapper<Superuser> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("number", number).set("phone", info);
                superuserMapper.update(null, updateWrapper);
                // 更新信息成功
                map.put("error_message", "success");
                return map;
            }
        }

        //更新的信息类型错误
        map.put("error_message", "更新的信息类型错误！");
        return map;
    }
}
