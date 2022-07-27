package edu.neu.madcourse.jotly;

import java.util.List;

public class User {
    private String email;
    private String name;
    private String username;
    private String password;
    private List<Journal> Journals;

    public String getUsername() {
        return username;
    }
    public List<Journal> getJournals() {
        return Journals;
    }

}
