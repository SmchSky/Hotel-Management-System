package com.hotelmanagementsystem.backend.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Superuser extends User{
    private String number;
    private String name;
    private String duty;
    
    public Superuser(String username, String password, String phone, String number, String name, String duty) {
        super(username, password, phone);
        this.number = number;
        this.name = name;
        this.duty = duty;
    }
}
