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
//
//    public String getEmail() {
//        return email;
//    }
//
//    public String getpassword() {
//        return password;
//    }
//
//    public void setpassword(String password) {
//        this.password = password;
//    }
//
//    public String getFullName() {
//        return userName;
//    }
//
//    public void setFullName(String fullName) {
//        this.userName = fullName;
//    }
//
//    public String getpassword1() {
//        return confirmPass;
//    }
//
//    public void setpassword1(String password) {
//        this.confirmPass = password;
//    }

}
