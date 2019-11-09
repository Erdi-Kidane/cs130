package com.example.spinit;

import java.util.ArrayList;

public class SpunPlaces {
    private ArrayList<Place>listOfSpunPlaces;
    private ArrayList<Place>listOfCheckPlaces;

    SpunPlaces(ArrayList<Place>listOfCheckPlaces, ArrayList<Place>listOfSpunPlaces){
        this.listOfCheckPlaces = listOfCheckPlaces;
        this.listOfSpunPlaces = listOfSpunPlaces;
    }
    void remove(Place currentPlace){
        //This is called in profile if someone want's to clean up their profile of either lists

        //****A database call will be needed at the end of this function****
    }

    void checkIn(Place currentPlace){
        //This is called in profile when we want to check in.
        //This will check if a currentPlace is in listOfSpunPlaces,
        //remove it, then push it into listOfCheckPlaces

        //****A database call will be needed at the end of this function****
    }

    void addPlace(Place currentPlace){
        //When a place is spun, it will call this function.
        //This will add a place into listOfSpunPlaces

        //****A database call will be needed at the end of this function****
    }
}
