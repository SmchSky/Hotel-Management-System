package com.hotelmanagementsystem.backend.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffUser {
    @TableId
    private String username;
    private String password;
    private String number;
    private String name;
    private String phone;
    private String duty;

}
