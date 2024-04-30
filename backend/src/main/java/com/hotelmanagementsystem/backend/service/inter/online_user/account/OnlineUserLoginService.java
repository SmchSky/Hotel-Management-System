package com.hotelmanagementsystem.backend.service.inter.online_user.account;

import java.util.Map;

public interface OnlineUserLoginService {
    Map<String, String> getToken(String username, String password);
}
