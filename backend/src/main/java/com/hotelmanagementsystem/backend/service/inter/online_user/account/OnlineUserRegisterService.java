package com.hotelmanagementsystem.backend.service.inter.online_user.account;

import java.util.Map;

public interface OnlineUserRegisterService {
    Map<String, String> register(String username, String password, String confirmed_password, String phone);
}
