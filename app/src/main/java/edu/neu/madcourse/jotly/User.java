package edu.neu.madcourse.jotly;

import java.util.ArrayList;
import java.util.List;

import edu.neu.madcourse.jotly.addingJournal.Journal;

public class User {
    private int userID;
    public String userName, email, password, confirmPass;
    private List<Journal> journals;

    public User() {
    }
    public User(int userID, String userName, String email, String password, String confirmPass) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.confirmPass = confirmPass;
        this.journals = new ArrayList<>();
        this.userID = userID;
    }

    public int getUserId() {
        return userID;
    }

    public List<Journal> getJournals() {
        return journals;
    }

}
