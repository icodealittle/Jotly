package edu.neu.madcourse.jotly;

public class User {
    private String email;
    private String name;
    private String username;
    private String password;

    public User() {}

    public User(String email, String password) {
        this.email = email;
        this.name = "New User";
        this.password = password;
        this.username = "New User";

    }

    public User(String email, String password, String username, String name) {
        this.email = email;
        this.name = name;
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
