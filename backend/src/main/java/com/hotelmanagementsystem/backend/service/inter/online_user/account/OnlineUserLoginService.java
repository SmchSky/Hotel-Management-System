package com.hotelmanagementsystem.backend.service.inter.online_user.account;

import java.util.Map;

public interface OnlineUserLoginService {
    Map<String, String> login(String username, String password);
}
