package com.example.spinit;

import android.util.ArraySet;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class PersonTest {
    //Person(ArrayList<String> listOfStatus, Set<String> listOfFriends, String username,
    //           SpunPlaces spunPlaces){
    @Test
    public void testingAllFunctions() {
        ArrayList<String> listOfStatus = new ArrayList<String>();
        listOfStatus.add("Just ate at Diddy Riese and their ice cream sandwhich was amazing");
        listOfStatus.add("That ramen at Kopan ramen was disgusting, 0/10");

        Set<String> listOfFriends = new HashSet<String>();
        listOfFriends.add("Defo");
        listOfFriends.add("x86-64");
        listOfFriends.add("HiddenGodHui");

        String username = "briantdu777";


        ArrayList<Place> listOfSpunPlaces = new ArrayList<Place>();
        ArrayList<Place> listOfCheckPlaces = new ArrayList<Place>();
        Place p1 = new Place(34.063144, -118.446743, "https://www.yelp.com/biz/diddy-riese-cookies-los-angeles-2");
        Place p2 = new Place(34.061025, -118.446023, "https://www.yelp.com/biz/kopan-ramen-ucla-los-angeles");
        Place p3 = new Place(34.062541, -118.446794, "https://www.yelp.com/biz/its-boba-time-los-angeles-29?osq=It%27s+Boba+Time");

        listOfCheckPlaces.add(p1);
        listOfCheckPlaces.add(p2);
        listOfSpunPlaces.add(p3);

        SpunPlaces sp1 = new SpunPlaces(listOfCheckPlaces, listOfSpunPlaces);

        Person per1 = new Person(listOfStatus, listOfFriends, username, sp1);

        //per1.getSpunPlaces().checkIn();

        //Tests all places functions
        assertEquals(34.063144, p1.getLongitude(), 0.1);
        assertEquals(-118.446743, p1.getLatitude(), 0.1);
        assertEquals("https://www.yelp.com/biz/diddy-riese-cookies-los-angeles-2", p1.getURL());

        assertEquals(true, per1.getSpunPlaces().remove(p1, "check"));
        assertEquals(false, per1.getSpunPlaces().remove(p1, "check"));
    }
}