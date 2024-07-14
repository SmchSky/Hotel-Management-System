package com.hotelmanagementsystem.backend.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PurchaseStaffUser extends User {
    private String number;
    private String name;
    private String duty;
    
    public PurchaseStaffUser(String username, String password, String number, String name, String phone, String duty) {
        super(username, password, phone);
        this.number = number;
        this.name = name;
        this.duty = duty;
    }
}
