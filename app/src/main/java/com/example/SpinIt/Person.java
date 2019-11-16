package com.example.SpinIt;

public class Person {

    public String title;
    public String startTime;
    public String endTime;
    public String day;

    public Person() {
    }

    public Person(String title, String startTime, String endTime, String day) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;
    }
    public void setDay (String s){
        this.day = s;
    }

}