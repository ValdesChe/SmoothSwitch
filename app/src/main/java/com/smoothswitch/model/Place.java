package com.smoothswitch.model;

public class Place {
    private int id;
    private String name;
    private String ringerMode;
    private double longitude;
    private double latitude;
    private double radius;
    private boolean isEnabled;

    public Place(){

    }

    public Place(int id, String name, String ringerMode,  double longitude, double latitude, double radius, boolean isEnabled) {
        this.id = id;
        this.name = name;
        this.ringerMode = ringerMode;
        this.longitude = longitude;
        this.latitude = latitude;
        this.radius = radius;
        this.isEnabled = isEnabled;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public String getRingerMode() {
        return ringerMode;
    }

    public void setRingerMode(String ringerMode) {
        this.ringerMode = ringerMode;
    }
}
