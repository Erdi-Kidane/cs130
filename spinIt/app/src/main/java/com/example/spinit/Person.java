package com.example.spinit;

import java.util.ArrayList;
import java.util.Set;

public class Person {
    private ArrayList<String> listOfStatus;
    private Set<String> listOfFriends;
    private String username;
    //private Restrict dietaryList;

    Person(ArrayList<String> listOfStatus, Set<String> listOfFriends, String username){
        this.listOfFriends = listOfFriends;
        this.listOfStatus = listOfStatus;
        this.username = username;
        //this.dietaryList = dietaryList
    }
    void updateStatus(String status){
        //Here we will add the status into our arrayList as another entry
        boolean checker = this.listOfStatus.add(status);
        if(!checker) {
            System.out.printf("Status cannot be updated");
        }
        //****A database call will be needed at the end of this function****
    }

    void addFriend(String username){
        boolean checker = true;
        //Do a database check to see if the username is a valid
        //one and set the checker to true if it exists

        if(!checker) {
            System.out.printf("User Cannot be found, no friend added");
            return;
        }
        checker = this.listOfFriends.add(username);
        if(!checker) {
            System.out.printf("User already is a friend");
            return;
        }
        //****A database call will be needed at the end of this function****
    }
    void setUsername(String username){
        boolean checker = true;
        //Do a database check to see if that username is used
        //if it is, then go ahead and set checker to false
        if(!checker) {
            System.out.printf("Username is taken");
            return;
        }
        this.username = username;
        //****A database call will be needed at the end of this function****
    }
    ArrayList<String> getListOfStatus(){return this.listOfStatus;}
    Set<String> getListOfFriends(){return this.listOfFriends;}
    String getUsername(){return this.username;}

}
