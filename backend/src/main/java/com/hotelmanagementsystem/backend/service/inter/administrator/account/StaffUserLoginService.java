package com.hotelmanagementsystem.backend.service.inter.administrator.account;

import java.util.Map;

public interface StaffUserLoginService {
    Map<String, String> login(String username, String password, String duty);
}
