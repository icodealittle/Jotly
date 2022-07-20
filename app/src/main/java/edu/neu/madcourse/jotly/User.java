package edu.neu.madcourse.jotly;

public class User {
    private String email;
    private String password;
    private String username;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.username = "New User";
    }

    public User(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNewPassword(String newPassword) {
        this.password = newPassword;
    }
}
