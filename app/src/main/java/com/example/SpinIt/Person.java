package com.example.SpinIt;
import java.io.Serializable;

public class Person implements Serializable {

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
    public String getDay (){return this.day;}
    public String getTitle () {return this.title;}
    public String getStartTime (){return this.startTime;    }
    public String getEndTime (){return this.endTime;}

}