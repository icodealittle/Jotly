package edu.neu.madcourse.jotly.journalIndex;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;


public class Entry {
    private String title;
    private String date;
    private String time;
    private String content;
    private String updated;


    public Entry() {
        //default
    }

    public Entry(String title, String date, String time) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.updated = date + " " + time;
    }

    public Entry(String title, String date, String time, String content) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.content = content;
        this.updated = date + " " + time;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> entry = new HashMap<>();
        entry.put("title", title);
        entry.put("date", date);
        entry.put("time", time);
        entry.put("content", content);
        entry.put("updated", updated);

        return entry;
    }

    public String getDate() {
        return this.date;
    }

    public String getTime() {
        return this.time;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public String getUpdated() {
        return this.updated;
    }

}
