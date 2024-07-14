package com.hotelmanagementsystem.backend.service.impl.administrator.hr_staff;

import com.hotelmanagementsystem.backend.mapper.StaffMapper;
import com.hotelmanagementsystem.backend.pojo.Staff;
import com.hotelmanagementsystem.backend.service.impl.administrator.superuser.AddStaffUserServiceImpl;
import com.hotelmanagementsystem.backend.service.inter.administrator.hr_staff.AddStaffService;
import com.hotelmanagementsystem.backend.utils.check.info_valid_check.StaffInfoValidCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class AddStaffServiceImpl implements AddStaffService {
    
    private final StaffMapper staffMapper;
    
    @Autowired
    public AddStaffServiceImpl(StaffMapper staffMapper) {
        this.staffMapper = staffMapper;
    }
    
    @Override
    public Map<String, String> addStaff(Map<String, String> data) {
        Map<String, String> map = new HashMap<>();
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
        // 生成新的员工编号
        String number = AddStaffUserServiceImpl.generateStaffNumber(staffMapper);
        if (number.charAt(0) != '2') {
            // 员工编号一定以2打头，故第一个字符不是2则说明编号没有成功生成
            map.put("message", number);
            return map;
        }
        // 插入新数据
        Staff new_staff = new Staff(number, name, sex, nation, id, LocalDate.parse(birthday), native_place,
                LocalDate.parse(entry_date), education, phone, duty, residential_address, basic_salary);
        staffMapper.insert(new_staff);
        map.put("message", "success");
        return map;
    }
}
