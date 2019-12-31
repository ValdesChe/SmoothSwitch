package com.smoothswitch;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;

import androidx.annotation.NonNull;

public class PhoneLocationManager {
    LocationManager locationManager;

    public PhoneLocationManager(@NonNull Activity activity) {
        locationManager = (android.location.LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
    }
}
