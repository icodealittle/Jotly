package edu.neu.madcourse.jotly;

import java.util.List;

public class Journal {
    private String title; //TODO title needs to be unique have check
    private List<Entry> entries;

    public Journal() {
        //default
    }
    public Journal (String title) {
        this.title = title;
    }

    public List<Entry> getEntries(){
        return entries;
    }
}
