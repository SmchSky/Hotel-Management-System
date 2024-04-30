package com.hotelmanagementsystem.backend.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Staff {
    @TableId
    private String number;
    private String name;
    private String sex;
    private String nation;
    private String id;
    private Date birthdate;
    private String nativePlace;
    private Date entryDate;
    private String education;
    private String phone;
    private String duty;
    private String residentialAddress;
    private String basicSalary;

}
