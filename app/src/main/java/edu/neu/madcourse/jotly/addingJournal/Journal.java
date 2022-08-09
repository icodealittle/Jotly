package edu.neu.madcourse.jotly.addingJournal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import edu.neu.madcourse.jotly.journalIndex.Entry;

public class Journal implements Serializable {
    private String name;
    private List<Entry> entryList = new ArrayList<>();

    public Journal() {}

    public Journal(String name) {
        this.name = name;
    }

    public Journal(String name, List<Entry> entryList) {
        this.name = name;
        this.entryList = entryList;
    }

    public void resetName(String newName) {
        this.name = newName;
    }

    public String getName() {
        return this.name;
    }

    public void addAnEntry(Entry anEntry) {
        this.entryList.add(anEntry);
    }
}
