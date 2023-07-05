package com.mauto.chd.mylocation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.TypedValue;

import com.mauto.chd.event_bus_connection.IntentServiceResult;
import com.mauto.chd.SessionManagerPackage.SessionManager;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mauto.chd.R;

import org.greenrobot.eventbus.EventBus;



public class MyLocationGoogleMap {
    public static final String LOGTAG = "Tracking";
    int tracking = 0;
    public static final String ACCURACY_COLOR = "#1800ce5b";
    public static final String ACCURACY_COLOR_BORDERS = "#8000ce5b";
//    private DatabaseReference mFirebaseDatabase;
//    private FirebaseDatabase mFirebaseInstance;
    private String userId="";
    private final Handler handler = new Handler(Looper.getMainLooper());
    private Context mContext;
    private MyLocationProvider mLocationProvider;
    private boolean isMyLocationCentered = false;
    private boolean zoomed = false;
    private final float accuracyStrokeWidth;
    SessionManager mSessionManager;
    private Circle accuracyCircle;
    private Marker locationMarker;
    private Marker bearingMarker;


    public MyLocationGoogleMap(Context context) {
        mContext = context;
        Resources r = context.getResources();
        float density = r.getDisplayMetrics().density;
        int size = (int) (256 * density);
        TileSystem.setTileSize(size);

        accuracyStrokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1,
                r.getDisplayMetrics()
        );
    }

    @SuppressLint("MissingPermission")
    public void moveToMyLocation(GoogleMap googleMap) {
        if (mLocationProvider != null) {
            Location location = mLocationProvider.getLastKnownLocation();
            if (googleMap != null && location != null) {
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
                googleMap.animateCamera(cameraUpdate, 1000, null);
            }
        }
    }

    public void addTo(final GoogleMap googleMap, Bitmap driver_marker_resized, BitmapDescriptor pickup,int mode) {
        addTo(googleMap, new GpsMyLocationProvider(mContext,mode), driver_marker_resized,pickup,mode);
    }

    public void addTo(final GoogleMap googleMap, MyLocationProvider myCustomLocationProvider, final Bitmap driver_marker_resized, final BitmapDescriptor pickup,int mode) {
        mLocationProvider = myCustomLocationProvider;
        mSessionManager = new SessionManager(mContext);
        mLocationProvider.startLocationProvider(new MyLocationConsumer() {
            @Override
            public void onLocationChanged(final Location location, MyLocationProvider source) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        Log.e("VSDK.onLocationChanged",
                                location.getLatitude() + " | " +
                                        location.getLongitude() + " | " +
                                        location.getBearing());

                        EventBus.getDefault().post(new IntentServiceResult(Activity.RESULT_OK, location.getLatitude()+","+location.getLongitude()+","+location.getBearing(), mContext.getString(R.string.gettingridecurrentlocation)));
                        Log.d("current_lats201",location.getLatitude()+"   "+location.getLongitude());

                        mSessionManager.setOnlineStatsu(location.getLatitude(),location.getLongitude(),location.getBearing(),"");

                        LatLng center = new LatLng(location.getLatitude(), location.getLongitude());

                        String ddxsfd  = String.valueOf((location.getSpeed()*3.6));
                        mSessionManager.setOnlinelocationspeed(""+location.getAccuracy()+","+ddxsfd);
                        final float radius = location.getAccuracy()
                                / (float) TileSystem.GroundResolution(location.getLatitude(),
                                googleMap.getCameraPosition().zoom);
                        if (accuracyCircle == null) {
                            accuracyCircle = googleMap.addCircle(new CircleOptions()
                                    .center(center)
                                    .radius(radius)
                                    .fillColor(Color.parseColor(ACCURACY_COLOR))
                                    .strokeColor(Color.parseColor(ACCURACY_COLOR_BORDERS))
                                    .strokeWidth(accuracyStrokeWidth)
                            );
                        } else {
                            accuracyCircle.setCenter(center);
                            accuracyCircle.setRadius(radius);
                        }

                        if (locationMarker == null) {
                           locationMarker = googleMap.addMarker(new MarkerOptions()
                                    .position(center)
                                    .anchor(0.5f, 0.5f)
                                   .title("current_loc")
                                   .icon(BitmapDescriptorFactory.fromBitmap(driver_marker_resized))
                            );
                        } else {
                            locationMarker.setPosition(center);
                        }

                        if (location.getBearing() == 0.0) {
                            if (bearingMarker != null) {
                                bearingMarker.remove();
                                bearingMarker = null;
                            }
                        } else {
                            if (bearingMarker == null) {
                                if (locationMarker != null) {
                                    locationMarker.remove();
                                    locationMarker = null;
                                }
                                bearingMarker = googleMap.addMarker(new MarkerOptions()
                                        .position(center)
                                        .flat(true)
                                        .anchor(0.5f, 0.5f)
                                        .rotation(location.getBearing())
                                        .icon(BitmapDescriptorFactory.fromBitmap(driver_marker_resized))
                                );
                            }
                            else
                                {
                                if (locationMarker != null) {
                                    locationMarker.remove();
                                    locationMarker = null;
                                }
                                bearingMarker.setPosition(center);
                                bearingMarker.setRotation(location.getBearing());
                                    googleMap.animateCamera(CameraUpdateFactory.newLatLng(center));
                            }
                        }
                        if (!isMyLocationCentered)
                        {
                            isMyLocationCentered = true;
                            moveToMyLocation(googleMap);
                        }
                    }
                });
            }
        });
    }

    public void removeFrom(GoogleMap googleMap) {


        Log.e("removed",
                "map removed");

        if (accuracyCircle != null) {
            accuracyCircle.remove();
            accuracyCircle = null;
        }
        if (locationMarker != null) {
            locationMarker.remove();
            locationMarker = null;
        }
        if (bearingMarker != null) {
            bearingMarker.remove();
            bearingMarker = null;
        }
        if (mLocationProvider != null) {
            mLocationProvider.destroy();
            mLocationProvider = null;
        }
        isMyLocationCentered = false;
    }



   /* private void  requestackkno(Context mContext,String ride_id,String driverlat,String driverlong,String bearing,String ridestatus)
    {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();
        if (tracking ==0)
        {
            userId = mFirebaseDatabase.push().getKey();
            TrackingPojo trackingdata = new TrackingPojo(ride_id,driverlat,driverlong,bearing,ridestatus);
            mFirebaseDatabase.child(ride_id).setValue(trackingdata);
            tracking++;
        }
        else
        {
            mFirebaseDatabase.child(ride_id).child("currentLat").setValue(driverlat);
            mFirebaseDatabase.child(ride_id).child("currentLon").setValue(driverlong);
            mFirebaseDatabase.child(ride_id).child("bearingValue").setValue(bearing);
            mFirebaseDatabase.child(ride_id).child("ridestatus").setValue(ridestatus);
        }
    }*/

}
