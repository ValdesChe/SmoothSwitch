package com.smoothswitch.helper;

import java.util.Date;

public class GPSPoint {

    private double lat, lon;
    private Date date;
    private String lastUpdate;

    public GPSPoint(Double latitude, Double longitude) {
        this.lat = latitude;
        this.lon = longitude;
    }

    public double getLatitude() {
        return lat;
    }

    public Date getDate() {
        return date;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public double getLongitude() {

        return lon;
    }

    @Override
    public String toString() {
        return "(" + lat + ", " + lon + ")";
    }
}

