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
    public boolean equals(Object o) {
        Place other = (Place) o;
        if(this.longitude == other.getLongitude() && this.latitude == other.getLatitude() && this.url.equals(other.getURL()))
            return true;
        else
            return false;
    }
    double getLongitude(){ return this.longitude;}
    double getLatitude(){ return this.latitude; }
    String getURL(){ return this.url; }
}
