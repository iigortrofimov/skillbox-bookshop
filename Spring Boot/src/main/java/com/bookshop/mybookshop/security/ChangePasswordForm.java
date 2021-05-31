package com.bookshop.mybookshop.security;

import lombok.Data;

@Data
public class ChangePasswordForm {
    private String email;
    private String phone;
    private String oldPassword;
    private String newPassword;
}
