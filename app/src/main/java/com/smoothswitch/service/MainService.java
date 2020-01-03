package com.smoothswitch.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import android.app.Activity;

import com.smoothswitch.helper.DbHelper;
import com.smoothswitch.helper.GPSPoint;
import com.smoothswitch.helper.LocationHelper;
import com.smoothswitch.helper.RingerMode;
import com.smoothswitch.helper.RingerModeManager;
import com.smoothswitch.helper.Workable;
import com.smoothswitch.model.Place;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class MainService extends Service {

    private static DbHelper dbHelper;
    private static RingerModeManager ringerModeManager;
    private static Activity mActivityRef;
    private List<Place> activePlaces;

    public static void updateActivity(Activity activity) {
        mActivityRef = activity;
    }

    @Override
    public void onCreate() {
        // The service is being created
        dbHelper = new DbHelper(this);
        activePlaces = new ArrayList<>();

        ringerModeManager = new RingerModeManager(mActivityRef);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
