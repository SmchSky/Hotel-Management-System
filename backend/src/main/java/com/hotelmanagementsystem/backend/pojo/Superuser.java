package com.hotelmanagementsystem.backend.pojo;

import lombok.Data;

@Data
public class Superuser extends StaffUser{
    public Superuser(String username, String password, String number, String name, String phone, String duty) {
        super(username, password, number, name, phone, duty);
    }

    public boolean equals(Object o) {
        return super.equals(o);
    }
}
