package org.example.web.dto;

import javax.validation.constraints.NotBlank;

public class SignUpForm {

    @NotBlank
    private String username;
    @NotBlank
    private String password;

    public SignUpForm(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public SignUpForm() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "SignUpForm{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
