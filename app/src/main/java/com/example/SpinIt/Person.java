package com.example.SpinIt;


import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import java.io.Serializable;
import java.util.ArrayList;

//public class Person implements Serializable {
//
//
//    public String title;
//    public String startTime;
//    public String endTime;
//    public String day;
//
//    //this variable test the arraylist datastructure
//    public ArrayList<String> dietaryList;
//
//    //this is to test SpunPlaces
//    private SpunPlaces spunPlaces;
//
//    public Person() {
//    }
//
//    /*
//    public Person(ArrayList<String> al){
//        this.dietaryList = al;
//    }
//     */
//    public Person(DataSnapshot snapshot){
//        this.title = snapshot.child("title").getValue(String.class);
//        this.startTime = snapshot.child("startTime").getValue(String.class);
//        this.endTime = snapshot.child("endTime").getValue(String.class);
//        this.day = snapshot.child("day").getValue(String.class);
//        GenericTypeIndicator<ArrayList<String>> stringList = new GenericTypeIndicator<ArrayList<String>>(){};
//        this.dietaryList = snapshot.child("dietaryList").getValue(stringList);
//        //this.spunPlaces = new SpunPlaces();
//        ArrayList<Place> tempCheck = new ArrayList<>();
//        ArrayList<Place> tempSpun = new ArrayList<>();
//        for(DataSnapshot temp: snapshot.child("spunPlaces").child("checkPlaces").getChildren()){
//                double lat = temp.child("latitude").getValue(double.class);
//                double longi = temp.child("longitude").getValue(double.class);
//                String url = temp.child("url").getValue(String.class);
//                Place tp = new Place(longi,lat,url);
//                tempCheck.add(tp);
//        }
//        for(DataSnapshot temp: snapshot.child("spunPlaces").child("spunPlaces").getChildren()){
//            double lat = temp.child("latitude").getValue(double.class);
//            double longi = temp.child("longitude").getValue(double.class);
//            String url = temp.child("url").getValue(String.class);
//            Place tp = new Place(longi,lat,url);
//            tempSpun.add(tp);
//        }
//        this.spunPlaces = new SpunPlaces(tempCheck, tempSpun);
//    }
//
//
//    public Person(String title, String startTime, String endTime, String day, ArrayList<String> al, SpunPlaces sp
//    ) {
//        this.title = title;
//        this.startTime = startTime;
//        this.endTime = endTime;
//        this.day = day;
//        this.dietaryList = al;
//        this.spunPlaces = sp;
//    }
//
//
//
//    public void setDay (String s){
//        this.day = s;
//    }
//    public String getDay (){return this.day;}
//    public String getTitle () {return this.title;}
//    public String getStartTime (){return this.startTime;}
//    public String getEndTime (){return this.endTime;}
//    public ArrayList<String> getDietaryList(){return this.dietaryList;}
//    public SpunPlaces getSpunPlaces(){return this.spunPlaces;}
//
//}


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Person implements Parcelable {
    private ArrayList<String> listOfStatus = new ArrayList<>();
    //private Set<String> listOfFriends;
    private SpunPlaces spunPlaces = new SpunPlaces();
    private PrefList dietaryList = new PrefList();
    private String currentUID;
/**************************************************/
//This is all for Parcelable stuff

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeParcelable(spunPlaces, flags);
        out.writeParcelable(dietaryList, flags);
        out.writeString(currentUID);
        out.writeStringList(listOfStatus);
    }
    public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
    private Person(Parcel in) {
        this.spunPlaces = in.readParcelable(SpunPlaces.class.getClassLoader());
        this.dietaryList = in.readParcelable(PrefList.class.getClassLoader());
        this.currentUID = in.readString();
        in.readStringList(listOfStatus);
    }
/************************************************/

    Person(String currentUID){
        this.listOfStatus = new ArrayList<>();
        this.dietaryList = new PrefList();
        this.spunPlaces = new SpunPlaces();
        this.currentUID = currentUID;

    }
    /**
     * This is the constructor for a person that has barely created an account and will instantiate everything as empty
     * @param username this is that person's username
     */
    Person(DataSnapshot snapshot){
        //this.listOfFriends = new HashSet<String>();
        GenericTypeIndicator<ArrayList<String>> stringList = new GenericTypeIndicator<ArrayList<String>>(){};
        if(snapshot.child("listOfStatus").exists())
            this.listOfStatus = snapshot.child("listOfStatus").getValue(stringList);
        else
            this.listOfStatus = new ArrayList<>();

        if(snapshot.child("spunPlaces").exists()) {
            ArrayList<Place> tempCheck = new ArrayList<>();
            ArrayList<Place> tempSpun = new ArrayList<>();
            for(DataSnapshot temp: snapshot.child("spunPlaces").child("checkPlaces").getChildren()){
                double lat = temp.child("latitude").getValue(double.class);
                double longi = temp.child("longitude").getValue(double.class);
                String url = temp.child("url").getValue(String.class);
                //EXPANSION OF PLACE
                Place tp = new Place(longi,lat,url);
                tempCheck.add(tp);
            }
            for(DataSnapshot temp: snapshot.child("spunPlaces").child("spunPlaces").getChildren()){
                double lat = temp.child("latitude").getValue(double.class);
                double longi = temp.child("longitude").getValue(double.class);
                String url = temp.child("url").getValue(String.class);
                //EXPANSION OF PLACE
                Place tp = new Place(longi,lat,url);
                tempSpun.add(tp);
            }
            this.spunPlaces = new SpunPlaces(tempCheck, tempSpun);
        }
        else
            this.spunPlaces = new SpunPlaces();

        if(snapshot.child("dietaryList").exists()){
            ArrayList<String> tempDiet = new ArrayList<>();
            for(DataSnapshot temp: snapshot.child("dietaryList").child("prefList").getChildren()){
                String tempDietS = temp.getValue(String.class);
                tempDiet.add(tempDietS);
            }
            PrefList pl = new PrefList();
            pl.createList(tempDiet);
            this.dietaryList = pl;
        }
        else
            this.dietaryList = new PrefList();

        this.currentUID = snapshot.child("currentUID").getValue(String.class);

    }
    /**
     * Updating a status add a new status onto a person's status, this the updated status will appear on a person's profile
     * @param status The status to be added onto profile
     * @return True if successful, false otherwise
     */
    public boolean updateStatus(String status){
        //Here we will add the status into our arrayList as another entry
        boolean checker = this.listOfStatus.add(status);
        if(!checker) {
            System.out.println("Status cannot be updated");
            return false;
        }
        //****A database call will be needed at the end of this function****
        return true;
    }

    public boolean updatePrefList(PrefList dietaryList){
        //do a database call and then return true
        this.dietaryList = dietaryList;
        DatabaseReference RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.child("Users").child(this.currentUID).child("Person").setValue(this);
        Log.d("tag", "updatedPrefList");
        return true;
    }
    /**
     * Adding a friend onto your friend's list, via email
     * @param username the username of the friend you want to add
     * @return True if successful, false otherwise
     */
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
    /**
     * getter for the list of statuses
     * @return list of statuses
     */
    public ArrayList<String> getListOfStatus(){return this.listOfStatus;}
    /**
     * getter for the list of friends
     * @return list of friends
     */
//    public Set<String> getListOfFriends(){return this.listOfFriends;}
    /**
     * getter for the SpunPlaces data structure, which contains both a spun place list and a check place list
     * @return SpunPlaces data structure
     */
    public SpunPlaces getSpunPlaces(){return this.spunPlaces;}
    /**
     * getter for the Dietary List
     * @return the Dietary list
     */
    public PrefList getDietaryList(){return this.dietaryList;}
    public String getCurrentUID(){return this.currentUID;}
}
