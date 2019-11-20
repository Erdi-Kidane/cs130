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


//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.Set;
//
//public class Person {
//    private ArrayList<String> listOfStatus;
//    private Set<String> listOfFriends;
//    private String username;
//    private SpunPlaces spunPlaces;
//    private ArrayList<String> dietaryList;
//    //private PrefList dietaryList;
//
//    /**
//     * This is the constructor for a person that has already used the app and has existing friends, status, spunplaces, and/or dietary list
//     * @param listOfStatus this is the list that is going to be hosted on the profile
//     * @param listOfFriends this is the list of friends that a person will have
//     * @param username this is that person's username
//     * @param spunPlaces this is the places that are both spun and checked in
//     * @param dietaryList this is the food restrictions that a person may have, like vegetarian or diabetic
//     */
//Person(ArrayList<String> listOfStatus, Set<String> listOfFriends, String username,
//       SpunPlaces spunPlaces, ArrayList<String> dietaryList //PrefList dietaryList,
//){
//    this.listOfFriends = listOfFriends;
//    this.listOfStatus = listOfStatus;
//    this.username = username;
//    this.spunPlaces = spunPlaces;
//    this.dietaryList = dietaryList;
//}
//    /**
//     * This is the constructor for a person that has barely created an account and will instantiate everything as empty
//     * @param username this is that person's username
//     */
//    Person(String username){
//        this.username = username;
//        this.listOfFriends = new HashSet<String>();
//        this.listOfStatus = new ArrayList<String>();
//        this.spunPlaces = new SpunPlaces();
//        this.dietaryList = new ArrayList<String>();
//        //this.dietaryList = new PrefList();
//    }
//
//    /**
//     * Updating a status add a new status onto a person's status, this the updated status will appear on a person's profile
//     * @param status The status to be added onto profile
//     * @return True if successful, false otherwise
//     */
//    public boolean updateStatus(String status){
//        //Here we will add the status into our arrayList as another entry
//        boolean checker = this.listOfStatus.add(status);
//        if(!checker) {
//            System.out.println("Status cannot be updated");
//            return false;
//        }
//        //****A database call will be needed at the end of this function****
//        return true;
//    }
//
//    /**
//     * Adding a friend onto your friend's list, via email
//     * @param username the username of the friend you want to add
//     * @return True if successful, false otherwise
//     */
//    public boolean addFriend(String username){
//        boolean checker = true;
//        //Do a database check to see if the username is a valid
//        //one and set the checker to true if it exists
//
//        if(!checker) {
//            System.out.println("User Cannot be found, no friend added");
//            return false;
//        }
//        checker = this.listOfFriends.add(username);
//        if(!checker) {
//            System.out.println("User already is a friend");
//            return false;
//        }
//        //****A database call will be needed at the end of this function****
//        return true;
//    }
//
//    /**
//     * If the user wants to change their username, they can if it is not taken already
//     * @param username New username
//     * @return True if successful, false otherwise
//     */
//    public boolean setUsername(String username){
//        boolean checker = true;
//        //Do a database check to see if that username is used
//        //if it is, then go ahead and set checker to false
//        if(this.username.equals(username)){
//            System.out.println("Username is same as current username");
//            return false;
//        }
//        if(!checker) {
//            System.out.println("Username is taken");
//            return false;
//        }
//        this.username = username;
//        //****A database call will be needed at the end of this function****
//        return true;
//    }
//    /**
//     * getter for the list of statuses
//     * @return list of statuses
//     */
//    public ArrayList<String> getListOfStatus(){return this.listOfStatus;}
//    /**
//     * getter for the list of friends
//     * @return list of friends
//     */
//    public Set<String> getListOfFriends(){return this.listOfFriends;}
//    /**
//     * getter for the username
//     * @return username
//     */
//    public String getUsername(){return this.username;}
//    /**
//     * getter for the SpunPlaces data structure, which contains both a spun place list and a check place list
//     * @return SpunPlaces data structure
//     */
//    public SpunPlaces getSpunPlaces(){return this.spunPlaces;}
//    /**
//     * getter for the Dietary List
//     * @return the Dietary list
//     */
//    public ArrayList<String> getDietaryList(){return this.dietaryList;}
//}
