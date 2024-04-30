package com.hotelmanagementsystem.backend.service.inter.administrator.account;

import java.util.Map;

public interface StaffUserLoginService {
    Map<String, String> getToken(String username, String password);
}
