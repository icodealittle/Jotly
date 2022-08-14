package edu.neu.madcourse.jotly.addingJournal;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> journal = new HashMap<>();
        journal.put("name", this.name);
        journal.put("entries", this.entryList);
        return journal;
    }

    public void resetName(String newName) {
        this.name = newName;
    }

    public String getName() {
        return this.name;
    }

    public void addAnEntry(Entry entry) {
        this.entryList.add(entry);
    }

    public List<Entry> getEntryList() {
        return entryList;
    }
}
