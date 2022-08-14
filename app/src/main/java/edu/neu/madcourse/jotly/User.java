package edu.neu.madcourse.jotly;

public class User {
    public String userName, email, password, confirmPass, userFullName;

    public User() {
    }

    public User(String userName, String email, String password, String confirmPass, String userFullName) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.confirmPass = confirmPass;
        this.userFullName = userFullName;
    }

}
