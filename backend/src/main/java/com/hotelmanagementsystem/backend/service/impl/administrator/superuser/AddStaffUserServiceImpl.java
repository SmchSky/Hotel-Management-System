package com.hotelmanagementsystem.backend.service.impl.administrator.superuser;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hotelmanagementsystem.backend.mapper.*;
import com.hotelmanagementsystem.backend.pojo.*;
import com.hotelmanagementsystem.backend.service.inter.administrator.superuser.AddStaffUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AddStaffUserServiceImpl implements AddStaffUserService {
    @Autowired
    private StaffMapper staffMapper;
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

    @Override
    public Map<String, String> addStaffUser(Map<String, String> data) {
        //取出信息
        String username = data.get("username");
        String password = data.get("password");
        String name = data.get("name");
        String phone = data.get("phone");
        String duty = data.get("duty");

        //用于返回的Map
        Map<String, String> map = new HashMap<>();

        //信息合法性检验
        if (username.length() == 0) {
            map.put("error_message", "用户名不能为空！");
            return map;
        }

        if (password.length() == 0) {
            map.put("error_message", "密码不能为空！");
            return map;
        }

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

        if (name.length() == 0) {
            map.put("error_message", "员工姓名不能为空！");
            return map;
        }

        if (name.length() > 20) {
            map.put("error_message", "员工姓名长度不能大于20！");
            return map;
        }

        //根据当前日期生成员工编号
        QueryWrapper<Staff> queryWrapper1 = new QueryWrapper<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String now_string = dateFormat.format(new java.util.Date());
        //利用正则表达式去除now_string中的连字符、冒号和空格
        String pre_number = now_string.replaceAll("-", "");
        //计算员工编号前缀为pre_number的元组的数量
        queryWrapper1.likeRight("number", pre_number);
        Long row = staffMapper.selectCount(queryWrapper1);

        //合成的新的员工编号
        String number;
        //如果今天已新建过员工账户
        if (row > 0) {
            //按照降序排序
            queryWrapper1.orderByDesc("number");
            //取出第一个元素
            queryWrapper1.last("LIMIT 1");
            Staff staff = staffMapper.selectOne(queryWrapper1);
            //获得最后两位数字
            int left_bit = Character.getNumericValue(staff.getNumber().charAt(8));
            int right_bit = Character.getNumericValue(staff.getNumber().charAt(9));
            //让右位加1
            right_bit += 1;
            //判断是否需要进位
            if (right_bit == 10) {
                //需要进位
                right_bit = 0;
                left_bit += 1;
                //判断今日员工数量是否已达上限
                if (left_bit == 10) {
                    map.put("error_message", "今日新建员工数量已满！");
                    return map;
                }
            }
            //赋值
            number = pre_number + (char) (left_bit + '0') + (char) (right_bit + '0');
        } else {
            //如果今天还未新建过员工账户
            int left_bit = 0;
            int right_bit = 1;
            //赋值
            number = pre_number + (char) (left_bit + '0') + (char) (right_bit + '0');
        }


        //根据duty判断username在相应的表中是否已经存在
        if ("酒店前台工作人员".equals(duty)) {
            QueryWrapper<FrontDeskStaff> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", username);
            List<FrontDeskStaff> users = frontDeskStaffMapper.selectList(queryWrapper);
            if (!users.isEmpty()) {
                map.put("error_message", "用户名已存在！");
                return map;
            }
        } else if ("餐厅前台工作人员".equals(duty)) {
            QueryWrapper<RestaurantStaff> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", username);
            List<RestaurantStaff> users = restaurantStaffMapper.selectList(queryWrapper);
            if (!users.isEmpty()) {
                map.put("error_message", "用户名已存在！");
                return map;
            }
        } else if ("财务管理员".equals(duty)) {
            QueryWrapper<FinancialStaff> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", username);
            List<FinancialStaff> users = financialStaffMapper.selectList(queryWrapper);
            if (!users.isEmpty()) {
                map.put("error_message", "用户名已存在！");
                return map;
            }
        } else if ("人事管理员".equals(duty)) {
            QueryWrapper<HrStaff> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", username);
            List<HrStaff> users = hrStaffMapper.selectList(queryWrapper);
            if (!users.isEmpty()) {
                map.put("error_message", "用户名已存在！");
                return map;
            }
        } else if ("采购人员".equals(duty)) {
            QueryWrapper<PurchaseStaff> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", username);
            List<PurchaseStaff> users = purchaseStaffMapper.selectList(queryWrapper);
            if (!users.isEmpty()) {
                map.put("error_message", "用户名已存在！");
                return map;
            }
        }

        String encodedPassword = passwordEncoder.encode(password);

        //插入到相应表
        if ("酒店前台工作人员".equals(duty)) {
            FrontDeskStaff frontDeskStaff = new FrontDeskStaff(username, encodedPassword, number, name, phone, duty);
            frontDeskStaffMapper.insert(frontDeskStaff);
        } else if ("餐厅前台工作人员".equals(duty)) {
            RestaurantStaff restaurantStaff = new RestaurantStaff(username, encodedPassword, number, name, phone, duty);
            restaurantStaffMapper.insert(restaurantStaff);
        } else if ("财务管理员".equals(duty)) {
            FinancialStaff financialStaff = new FinancialStaff(username, encodedPassword, number, name, phone, duty);
            financialStaffMapper.insert(financialStaff);
        } else if ("人事管理员".equals(duty)) {
            HrStaff hrStaff = new HrStaff(username, encodedPassword, number, name, phone, duty);
            hrStaffMapper.insert(hrStaff);
        } else if ("采购人员".equals(duty)) {
            PurchaseStaff purchaseStaff = new PurchaseStaff(username, encodedPassword, number, name, phone, duty);
            purchaseStaffMapper.insert(purchaseStaff);
        }

        //将员工信息插入到员工表中
        Staff new_staff = new Staff(number, name, null, null, null, null, null, null, null, phone, duty, null, null);
        staffMapper.insert(new_staff);
        map.put("error_message", "success");
        return map;
    }
}
