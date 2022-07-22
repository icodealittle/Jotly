package edu.neu.madcourse.jotly;

public class User {

    public String userName, email, password, confirmPass;

    public User() {
    }

    public User(String userName, String email, String password, String confirmPass) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.confirmPass = confirmPass;
    }
}
