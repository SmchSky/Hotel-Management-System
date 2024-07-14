package com.hotelmanagementsystem.backend.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OnlineUser extends User {
    public OnlineUser(String username, String password, String phone) {
        super(username, password, phone);
    }
}
