package com.example.cloudlibrary.util;

import com.example.cloudlibrary.CLApplication;

public class Comment{
    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public Comment(String title, CLApplication clApplication){
        this.title = title;
        this.author = clApplication.getUserName();
    }
    public Comment(String title){
        this.title = title;
        this.author = "unknown";
    }
    public Comment(){}
    private String title;
    private String author;
    private String date;

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    private int power = -1;
    public String getTitle(){
        return title;
    }

    public int getVisited() {
        return visited;
    }

    public void setVisited(int visited) {
        this.visited = visited;
    }

    private int visited = -1;

}