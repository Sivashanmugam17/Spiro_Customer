package com.mauto.chd.view_model_with_repositary_main;


import android.app.Activity;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Location;

import com.mauto.chd.event_bus_connection.IntentServiceResult;
import com.mauto.chd.R;

import org.greenrobot.eventbus.EventBus;



/**
 * location update service continues to running and getting location information
 */
public class LocationUpdatesService extends JobService implements LocationUpdatesComponent.ILocationProvider {

    private static final String TAG = LocationUpdatesService.class.getSimpleName();
    public static final int LOCATION_MESSAGE = 9999;

    private LocationUpdatesComponent locationUpdatesComponent;

    public LocationUpdatesService() {
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        locationUpdatesComponent.onStop();
        return false;
    }

    @Override
    public void onCreate() {

        locationUpdatesComponent = new LocationUpdatesComponent(this);
        locationUpdatesComponent.onCreate(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        locationUpdatesComponent.onStart();
        return START_STICKY;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return true; // Ensures onRebind() is called when a client re-binds.
    }

    @Override
    public void onDestroy() {
        locationUpdatesComponent.onStop();
    }


    private void sendMessage(int messageID, Location location)
    {
        EventBus.getDefault().post(new IntentServiceResult(Activity.RESULT_OK, location.getLatitude()+","+location.getLongitude()+","+location.getAccuracy(), getString(R.string.getlocation)));
    }

    @Override
    public void onLocationUpdate(Location location) {
        sendMessage(LOCATION_MESSAGE, location);
    }
}
