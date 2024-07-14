package com.hotelmanagementsystem.backend.service.impl.administrator.superuser;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hotelmanagementsystem.backend.mapper.*;
import com.hotelmanagementsystem.backend.pojo.*;
import com.hotelmanagementsystem.backend.service.inter.administrator.superuser.AddStaffUserService;
import com.hotelmanagementsystem.backend.utils.check.info_valid_check.UserInfoValidCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class AddStaffUserServiceImpl implements AddStaffUserService {
    
    private final PasswordEncoder passwordEncoder;
    private final StaffMapper staffMapper;
    private final FinancialStaffMapper financialStaffMapper;
    private final FrontDeskStaffMapper frontDeskStaffMapper;
    private final HrStaffMapper hrStaffMapper;
    private final PurchaseStaffMapper purchaseStaffMapper;
    private final RestaurantStaffMapper restaurantStaffMapper;
    
    @Autowired
    public AddStaffUserServiceImpl(PasswordEncoder passwordEncoder, StaffMapper staffMapper, FinancialStaffMapper financialStaffMapper, FrontDeskStaffMapper frontDeskStaffMapper, HrStaffMapper hrStaffMapper, PurchaseStaffMapper purchaseStaffMapper, RestaurantStaffMapper restaurantStaffMapper) {
        this.staffMapper = staffMapper;
        this.passwordEncoder = passwordEncoder;
        this.financialStaffMapper = financialStaffMapper;
        this.frontDeskStaffMapper = frontDeskStaffMapper;
        this.hrStaffMapper = hrStaffMapper;
        this.purchaseStaffMapper = purchaseStaffMapper;
        this.restaurantStaffMapper = restaurantStaffMapper;
    }
    
    /**
     * 根据当前日期生成员工编号，格式为："日期字符串+dd"（每天最多新建员工100个，尾缀从00到99）
     *
     * @param staffMapper 员工信息的mapper
     * @return 如果成功生成新的员工编号则返回员工编号，如果未成功生成则返回错误信息
     */
    public static String generateStaffNumber(StaffMapper staffMapper) {
        // 当前日期的string作为员工编号前缀
        String pre_number = LocalDate.now().toString().replaceAll("-", "");
        // 计算员工编号前缀为pre_number的元组的数量
        QueryWrapper<Staff> queryWrapper = new QueryWrapper<>();
        Long row = staffMapper.selectCount(queryWrapper.likeRight("number", pre_number));
        String number;
        if (row > 0) {
            Staff staff = staffMapper.selectOne(queryWrapper.orderByDesc("number").last("LIMIT 1"));
            int left_digit = Character.getNumericValue(staff.getNumber().charAt(8));
            int right_digit = Character.getNumericValue(staff.getNumber().charAt(9));
            right_digit += 1;
            if (right_digit == 10) {
                right_digit = 0;
                left_digit += 1;
                if (left_digit == 10) {
                    return "今日新建员工数量已满！";
                }
            }
            number = pre_number + (char) (left_digit + '0') + (char) (right_digit + '0');
        } else {
            number = pre_number + "00";
        }
        return number;
    }
    
    @Override
    public Map<String, String> addStaffUser(Map<String, String> data) {
        Map<String, String> map = new HashMap<>();
        String username = data.get("username");
        String password = data.get("password");
        String confirmed_password = data.get("confirmed_password");
        String phone = data.get("phone");
        String name = data.get("name");
        String duty = data.get("duty");
        // 信息合法性检验
        String message = UserInfoValidCheck.checkStaffUserInfoValid(username, password, confirmed_password, phone, name, duty);
        if (!message.equals("success")) {
            map.put("message", message);
            return map;
        }
        // 生成新的员工编号
        String number = generateStaffNumber(staffMapper);
        if (number.charAt(0) != '2') {
            // 员工编号一定以2打头，故第一个字符不是2则说明编号没有成功生成
            map.put("message", number);
            return map;
        }
        // 检查用户名是否存在
        if (isUsernameExists(duty, username,phone)) {
            map.put("message", "用户名或手机号已存在！");
            return map;
        }
        // 插入到相应用户表
        insertStaffUser(username, passwordEncoder.encode(password), number, name, phone, duty);
        // 将员工信息插入到员工表中
        Staff new_staff = new Staff(number, name, null, null, null, null, null, null, null, phone, duty, null, null);
        staffMapper.insert(new_staff);
        map.put("message", "success");
        return map;
    }
    
    /**
     * @param duty     员工职务
     * @param username 员工账户用户名
     * @return 如果员工账户用户名已存在则返回true，否则返回false
     */
    private boolean isUsernameExists(String duty, String username, String phone) {
        return switch (duty) {
            case "酒店前台工作人员" ->
                    !frontDeskStaffMapper.selectList(new QueryWrapper<FrontDeskStaffUser>().eq("username", username).or().eq("phone", phone)).isEmpty();
            case "餐厅前台工作人员" ->
                    !restaurantStaffMapper.selectList(new QueryWrapper<RestaurantStaffUser>().eq("username", username).or().eq("phone", phone)).isEmpty();
            case "财务管理员" ->
                    !financialStaffMapper.selectList(new QueryWrapper<FinancialStaffUser>().eq("username", username).or().eq("phone", phone)).isEmpty();
            case "人事管理员" ->
                    !hrStaffMapper.selectList(new QueryWrapper<HrStaffUser>().eq("username", username).or().eq("phone", phone)).isEmpty();
            case "采购人员" ->
                    !purchaseStaffMapper.selectList(new QueryWrapper<PurchaseStaffUser>().eq("username", username).or().eq("phone", phone)).isEmpty();
            default -> false;
        };
    }
    
    private void insertStaffUser(String username, String encodedPassword, String number, String name, String phone, String duty) {
        switch (duty) {
            case "酒店前台工作人员" ->
                    frontDeskStaffMapper.insert(new FrontDeskStaffUser(username, encodedPassword, number, name, phone, duty));
            case "餐厅前台工作人员" ->
                    restaurantStaffMapper.insert(new RestaurantStaffUser(username, encodedPassword, number, name, phone, duty));
            case "财务管理员" ->
                    financialStaffMapper.insert(new FinancialStaffUser(username, encodedPassword, number, name, phone, duty));
            case "人事管理员" ->
                    hrStaffMapper.insert(new HrStaffUser(username, encodedPassword, number, name, phone, duty));
            case "采购人员" ->
                    purchaseStaffMapper.insert(new PurchaseStaffUser(username, encodedPassword, number, name, phone, duty));
        }
    }
}