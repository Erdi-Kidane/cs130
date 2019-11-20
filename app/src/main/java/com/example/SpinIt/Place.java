package com.example.SpinIt;

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

    /**
     **
     * This is needed for checking equality of Places, which is automatically used
     *
     * @param o This is the other object
     * @return true if the longitude, latitude AND URL are equivalent, otherwise it's false
     *
     */
    public boolean equals(Object o) {
        Place other = (Place) o;
        if(this.longitude == other.getLongitude() && this.latitude == other.getLatitude() && this.url.equals(other.getURL()))
            return true;
        else
            return false;
    }

    /**
     * getter for the longitude of the place
     * @return longitude value
     */
    public double getLongitude(){ return this.longitude;}
    /**
     * getter for the latitude of the place
     * @return latitude value
     */
    public double getLatitude(){ return this.latitude;}
    /**
     * getter for the URL for the yelp of the place
     * @return URL for the yelp of the place
     */
    public String getURL(){ return this.url; }
}
