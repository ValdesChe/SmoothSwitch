package com.smoothswitch.helper;

import android.location.Location;
import android.os.Looper;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.smoothswitch.MyApp;


public class LocationHelper {

    private static final LocationHelper instance = new LocationHelper();

    private static final String TAG = LocationHelper.class.getSimpleName();

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback locationCallback;
    private LocationSettingsRequest locationSettingsRequest;
    private Workable<GPSPoint> workable;

    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 1000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 1000;

    private LocationHelper() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        locationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        this.locationSettingsRequest = builder.build();

        this.locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult); // why? this. is. retarded. Android.
                Location currentLocation = locationResult.getLastLocation();

                GPSPoint gpsPoint = new GPSPoint(currentLocation.getLatitude(), currentLocation.getLongitude());
                Log.i(TAG, "Location Callback results: " + gpsPoint);
                if (null != workable)
                    workable.work(gpsPoint);
            }
        };
        this.mFusedLocationClient = LocationServices.getFusedLocationProviderClient(MyApp.getAppContext());
        this.mFusedLocationClient.requestLocationUpdates(locationRequest,
                this.locationCallback, Looper.myLooper());
    }

    public static LocationHelper instance() {
        return instance;
    }

    public void onChange(Workable<GPSPoint> workable) {
        this.workable = workable;
    }

    public LocationSettingsRequest getLocationSettingsRequest() {
        return this.locationSettingsRequest;
    }

    /**
     *
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return
     */

    public static double distance(double lat1, double lng1, double lat2, double lng2) {
        double a = (lat1 - lat2) * distPerLat(lat1);
        double b = (lng1 - lng2) * distPerLng(lat1);
        return Math.sqrt(a * a + b * b)/1000000;
    }

    private static double distPerLng(double lat) {
        return 0.0003121092 * Math.pow(lat, 4) + 0.0101182384 * Math.pow(lat, 3) - 17.2385140059 * lat * lat
                + 5.5485277537 * lat + 111301.967182595;
    }

    private static double distPerLat(double lat) {
        return -0.000000487305676 * Math.pow(lat, 4) - 0.0033668574 * Math.pow(lat, 3) + 0.4601181791 * lat * lat
                - 1.4558127346 * lat + 110579.25662316;
    }

    public void stop() {
        Log.i(TAG, "stop() Stopping location tracking");
        this.mFusedLocationClient.removeLocationUpdates(this.locationCallback);
    }

}