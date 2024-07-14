package com.hotelmanagementsystem.backend.utils.check.info_valid_check;

import org.springframework.lang.Nullable;

public class UserInfoValidCheck {
    
    /**
     * 检查OnlineUser对象的某字段信息是否合法。注意全部参数都可为null，传入哪几个参数就检验哪几个参数，但是要保证密码和确认密码不能一个为null一个不为null。
     *
     * @param username           用户名
     * @param password           密码
     * @param confirmed_password 确认密码
     * @param phone              手机号
     * @return 要检查的字段合法，返回success，否则返回不合法的原因
     */
    public static String checkOnlineUserInfoValid(@Nullable String username, @Nullable String password, @Nullable String confirmed_password, @Nullable String phone) {
        String message = "success";
        // 检查用户名
        if (username != null) {
            if (username.isEmpty()) {
                message = "用户名不能为空！";
                return message;
            }
            if (username.indexOf(' ') >= 0) {
                message = "用户名不能包含空格！";
                return message;
            }
            if (username.length() > 20) {
                message = "用户名长度不能大于20！";
                return message;
            }
        }
        // 检查密码和确认密码
        if (password != null && confirmed_password != null) {
            if (password.isEmpty()) {
                message = "密码不能为空！";
                return message;
            }
            if (confirmed_password.isEmpty()) {
                message = "确认密码不能为空！";
                return message;
            }
            if (password.length() > 20) {
                message = "密码长度不能大于20！";
                return message;
            }
            if (!password.equals(confirmed_password)) {
                message = "两次输入的密码不一致！";
                return message;
            }
        }
        // 方法使用错误
        if ((password == null && confirmed_password != null) || (password != null && confirmed_password == null)) {
            message = "方法使用错误，请学习方法的使用！";
            return message;
        }
        // 检查手机号
        if (phone != null) {
            if (phone.length() != 11) {
                message = "手机号格式错误！";
                return message;
            }
        }
        return message;
    }
    
    /**
     * 检查工作人员用户的某字段信息是否合法。注意全部参数都可为null，传入哪几个参数就检验哪几个参数，但是要保证密码和确认密码不能一个为null一个不为null。
     *
     * @param username           用户名
     * @param password           密码
     * @param confirmed_password 确认密码
     * @param phone              手机号
     * @param name               真实姓名
     * @param duty               职务
     * @return 要检查的字段合法，返回success，否则返回不合法的原因
     */
    public static String checkStaffUserInfoValid(@Nullable String username, @Nullable String password, @Nullable String confirmed_password, @Nullable String phone, @Nullable String name, @Nullable String duty) {
        // 先检查前4个字段
        String message = checkOnlineUserInfoValid(username, password, confirmed_password, phone);
        // 前4个字段中有问题
        if (!message.equals("success")) {
            return message;
        }
        // 检查姓名
        if (name != null) {
            if (name.isEmpty()) {
                message = "员工姓名不能为空！";
                return message;
            }
            if (name.length() > 20) {
                message = "员工姓名长度不能大于20！";
                return message;
            }
        }
        // 检查职务
        if (duty != null) {
            if (!duty.equals("酒店前台工作人员") && !duty.equals("餐厅前台工作人员") && !duty.equals("财务管理员") && !duty.equals("人事管理员") && !duty.equals("采购人员") && !duty.equals("超级用户")) {
                message = "职位错误！";
                return message;
            }
        }
        return message;
    }
    
}
