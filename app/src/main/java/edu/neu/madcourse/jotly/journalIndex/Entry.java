package edu.neu.madcourse.jotly.journalIndex;

import android.media.Image;

import java.io.Serializable;

/**
 * This class presents a journal object
 */
public class Entry implements Serializable {
    private String date;
    private String time;
    private String title;
    private String content = null;
    private String location = null;
    private String weather = null;
    private Image mood = null;
    private String updateTime;

    public Entry() {}

    public Entry(String date, String time, String title){
        this.date = date;
        this.time = time;
        this.title = title;
        this.updateTime = date + " " + time;
    }

    public Entry(String date, String time, String title, String content){
        this.date = date;
        this.time = time;
        this.title = title;
        this.content = content;
        this.updateTime = date + " " + time;
    }

    public Entry(String date, String time, String title, String content, String location){
        this.date = date;
        this.time = time;
        this.title = title;
        this.content = content;
        this.location = location;
        this.updateTime = date + " " + time;
    }

    public Entry(String date, String time, String title, String content, Image mood){
        this.date = date;
        this.time = time;
        this.title = title;
        this.content = content;
        this.mood = mood;
        this.updateTime = date + " " + time;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public void setWeather(String weather){
        this.weather = weather;
    }

    public void setMood(Image mood){
        this.mood = mood;
    }

    public void setUpdateTime(String updateTime){
        this.updateTime = updateTime;
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

    public String getLocation(){
        return this.location;
    }

    public String getWeather(String weather){
        return this.weather;
    }

    public Image getMood(Image mood){
        return this.mood;
    }

    public void changeContent(String newContent) {
        this.content = newContent;
    }

    public void changeTitle(String newTitle) {
        this.title = newTitle;
    }
}
