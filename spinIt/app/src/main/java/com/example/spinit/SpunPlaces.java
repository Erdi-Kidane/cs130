package com.example.spinit;

import java.util.ArrayList;

public class SpunPlaces {
    private ArrayList<Place>listOfSpunPlaces;
    private ArrayList<Place>listOfCheckPlaces;

    SpunPlaces(ArrayList<Place>listOfCheckPlaces, ArrayList<Place>listOfSpunPlaces){
        this.listOfCheckPlaces = listOfCheckPlaces;
        this.listOfSpunPlaces = listOfSpunPlaces;
    }
    void remove(Place currentPlace, boolean spunOrCheck){
        //This is called in profile if someone want's to clean up their profile of either lists
        boolean checker;
        if(spunOrCheck){
            //if true remove it from the spun places
            checker = this.listOfSpunPlaces.remove(currentPlace);
        }
        else{
            //else remove if from the check places
            checker = this.listOfCheckPlaces.remove(currentPlace);
        }
        if(!checker){
            System.out.printf("Error with removing a place, check SpunPlaces.java");
        }
        //****A database call will be needed at the end of this function****
    }

    void checkIn(Place currentPlace){
        //This is called in profile when we want to check in.
        //This will check if a currentPlace is in listOfSpunPlaces,
        //remove it, then push it into listOfCheckPlaces
        boolean checker;
        checker = this.listOfSpunPlaces.remove(currentPlace);
        if(!checker){
            System.out.printf("Place doesn't exist in checkIn, check SpunPlaces.java");
        }
        checker = this.listOfCheckPlaces.add(currentPlace);
        if(!checker){
            System.out.printf("Place can't be added from spun places, check SpunPlaces.java");
        }
        //****A database call will be needed at the end of this function****
    }

    void addPlace(Place currentPlace){
        //When a place is spun, it will call this function.
        //This will add a place into listOfSpunPlaces
        boolean checker;
        checker = this.listOfSpunPlaces.add(currentPlace);
        if(!checker){
            System.out.printf("Place can't be added to spun places, check SpunPlaces.java");
        }
        //****A database call will be needed at the end of this function****
    }
    ArrayList<Place> getSpunPlaces(){
        return this.listOfSpunPlaces;
    }
    ArrayList<Place> getCheckPlaces(){
        return this.listOfCheckPlaces;
    }
}
