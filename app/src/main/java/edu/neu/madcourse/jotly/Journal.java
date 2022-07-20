package edu.neu.madcourse.jotly;

/**
 * This class presents a journal object
 */
public class Journal {
    private String date;
    private String time;
    private String title;
    private String content;
    private String location;

    public Journal(String date, String time, String title, String content){
        this.date = date;
        this.time = time;
        this.title = title;
        this.content = content;
        this.location = null;
    }

    public Journal(String date, String time, String title, String content, String location){
        this.date = date;
        this.time = time;
        this.title = title;
        this.content = content;
        this.location = location;
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

    // Return null if no location recorded
    public String getLocation() {
        return this.location;
    }

    public void changeContent(String newContent) {
        this.content = newContent;
    }

    public void changeTitle(String newTitle) {
        this.title = newTitle;
    }
}
