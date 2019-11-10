package com.example.spinit;

public class Place {
    private double longitude;
    private double latitude;
    private String url;
    Place(double longitude, double latitude, String url)
    {
        this.longitude = longitude;
        this.latitude = latitude;
        this.url = url;
    }
    double getLongitude(){ return this.longitude;}
    double getLatitude(){ return this.latitude; }
    String getURL(){ return this.url; }
}
