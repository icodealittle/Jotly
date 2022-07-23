package edu.neu.madcourse.jotly;

public class Journal {
    private String name;

    public Journal(String name) {
        this.name = name;
    }

    public void resetName(String newName) {
        this.name = newName;
    }
}
