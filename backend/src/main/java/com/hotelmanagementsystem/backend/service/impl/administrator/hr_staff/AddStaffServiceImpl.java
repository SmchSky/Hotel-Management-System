package com.hotelmanagementsystem.backend.service.impl.administrator.hr_staff;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hotelmanagementsystem.backend.mapper.StaffMapper;
import com.hotelmanagementsystem.backend.pojo.Room;
import com.hotelmanagementsystem.backend.pojo.Staff;
import com.hotelmanagementsystem.backend.service.inter.administrator.hr_staff.AddStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AddStaffServiceImpl implements AddStaffService {
    @Autowired
    private StaffMapper staffMapper;

    @Override
    public Map<String, String> addStaff(Map<String, String> data) {
        //返回的Map
        Map<String, String> map = new HashMap<>();
        //取出信息
        String name = data.get("name");
        String sex = data.get("sex");
        String id = data.get("id");
        String phone = data.get("phone");
        String duty = data.get("duty");
        String basic_salary = data.get("basic_salary");
        String nation = data.get("nation");
        String birthday = data.get("birthday");
        String native_place = data.get("native_place");
        String entry_date = data.get("entry_date");
        String education = data.get("education");
        String residential_address = data.get("residential_address");

        //合法性判断
        if (name.length() == 0) {
            map.put("error_message", "姓名不能为空！");
            return map;
        }
        if (id.length() != 18) {
            map.put("error_message", "身份证号格式错误！");
            return map;
        }
        if (phone.length() != 11) {
            map.put("error_message", "手机号格式错误！");
            return map;
        }
        if (duty.length() == 0) {
            map.put("error_message", "职务不能为空！");
            return map;
        }
        if (basic_salary.length() == 0) {
            map.put("error_message", "基本工资不能为空！");
            return map;
        }
        if (nation.length() == 0) {
            map.put("error_message", "民族不能为空！");
            return map;
        }
        if (birthday.length() == 0) {
            map.put("error_message", "出生日期不能为空！");
            return map;
        }
        if (native_place.length() == 0) {
            map.put("error_message", "籍贯不能为空！");
            return map;
        }
        if (entry_date.length() == 0) {
            map.put("error_message", "入职时间不能为空！");
            return map;
        }
        if (residential_address.length() == 0) {
            map.put("error_message", "家庭住址不能为空！");
            return map;
        }

        //将birthday和entry_date转化为Date类型
        Date birthday_date = Date.valueOf(birthday);
        Date entry_date_date = Date.valueOf(entry_date);

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

        //插入新数据
        Staff staff_new = new Staff(number, name, sex, nation, id, birthday_date, native_place, entry_date_date,
                education, phone, duty, residential_address, basic_salary);
        staffMapper.insert(staff_new);
        map.put("error_message", "success");
        return map;
    }
}
