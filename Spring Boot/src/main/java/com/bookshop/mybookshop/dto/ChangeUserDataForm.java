package com.bookshop.mybookshop.dto;

import lombok.Data;

@Data
public class ChangeUserDataForm {

    private String name;
    private String mail;
    private String phone;
    private String password;
    private String passwordReply;
}
