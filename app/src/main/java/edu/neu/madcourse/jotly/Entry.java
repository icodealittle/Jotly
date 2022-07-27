package edu.neu.madcourse.jotly;

import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Entry {
    private String title;
    private String content;
    private Date created;
    private Date updated;

    public Entry() {
        //default
    }

    //constructor for creation of a new entry
    public Entry (String title, String content, Date created) {
        this.title = title;
        this.content = content;
        this.created = created;
    }

    // constructor for updating an entry
    public Entry (Date updated, String title, String content) {
        this.title = title;
        this.content = content;
        this.updated = updated;

    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> entry = new HashMap<>();
        entry.put("title", title);
        entry.put("created", created);
        entry.put("updated", updated);
        entry.put("content", content);

        return entry;
    }


}
