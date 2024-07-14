package com.hotelmanagementsystem.backend.service.impl.administrator.hr_staff;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hotelmanagementsystem.backend.mapper.StaffMapper;
import com.hotelmanagementsystem.backend.pojo.Staff;
import com.hotelmanagementsystem.backend.service.inter.administrator.hr_staff.UpdateStaffService;
import com.hotelmanagementsystem.backend.utils.check.info_valid_check.StaffInfoValidCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateStaffServiceImpl implements UpdateStaffService {
    
    private final StaffMapper staffMapper;
    
    @Autowired
    public UpdateStaffServiceImpl(StaffMapper staffMapper) {
        this.staffMapper = staffMapper;
    }
    
    @Override
    public Map<String, String> updateStaff(Map<String, String> data) {
        Map<String, String> map = new HashMap<>();
        String number = data.get("number");
        String name = data.get("name");
        String sex = data.get("sex");
        String id = data.get("id");
        String phone = data.get("phone");
        String duty = data.get("duty");
        String basic_salary = data.get("basic_salary");
        String nation = data.get("nation");
        String birthday = data.get("birthdate");
        String native_place = data.get("native_place");
        String entry_date = data.get("entry_date");
        String education = data.get("education");
        String residential_address = data.get("residential_address");
        // 合法性检验
        String message = StaffInfoValidCheck.checkStaffInfoValid(name, id, phone, duty, basic_salary, nation, birthday, native_place, entry_date, residential_address);
        if (!message.equals("success")) {
            map.put("message", message);
            return map;
        }
        // 更新员工信息
        Staff staff = new Staff(number, name, sex, nation, id, LocalDate.parse(birthday), native_place, LocalDate.parse(entry_date), education, phone, duty, residential_address, basic_salary);
        UpdateWrapper<Staff> updateWrapper = new UpdateWrapper<>();
        staffMapper.update(staff, updateWrapper.eq("number", number));
        map.put("message", "success");
        return map;
    }
}
