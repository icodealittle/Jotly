package edu.neu.madcourse.jotly;

import java.util.ArrayList;
import java.util.List;

public class Journal {
    private int journalID;
    private String title; //TODO title needs to be unique have check
    private List<Entry> entries;

    public Journal() {
        //default
    }

    public Journal (String title, int journalID) {
        this.title = title;
        this.journalID = journalID;
        this.entries = new ArrayList<>();
    }
    public List<Entry> getEntries(){
        return entries;
    }
}
