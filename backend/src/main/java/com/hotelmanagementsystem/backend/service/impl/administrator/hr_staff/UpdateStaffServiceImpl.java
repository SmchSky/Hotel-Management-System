package com.hotelmanagementsystem.backend.service.impl.administrator.hr_staff;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hotelmanagementsystem.backend.mapper.StaffMapper;
import com.hotelmanagementsystem.backend.pojo.Staff;
import com.hotelmanagementsystem.backend.service.inter.administrator.hr_staff.UpdateStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateStaffServiceImpl implements UpdateStaffService {
    @Autowired
    private StaffMapper staffMapper;

    @Override
    public Map<String, String> updateStaff(Map<String, String> data) {
        //返回的Map
        Map<String, String> map = new HashMap<>();
        //取出信息
        String number = data.get("number");
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
        if (birthday == null) {
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


        //找到要修改的员工
        UpdateWrapper<Staff> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("number", number);

        //更新员工信息
        Staff staff = new Staff(number, name, sex, nation, id, birthday_date, native_place, entry_date_date,
                education, phone, duty, residential_address, basic_salary);
        staffMapper.update(staff, updateWrapper);

        map.put("error_message", "success");
        return map;
    }
}
