package org.example.web.dto;

public class SignUpForm {
    private String username;
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
