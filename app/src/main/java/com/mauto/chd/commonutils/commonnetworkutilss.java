package com.mauto.chd.commonutils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;


public class commonnetworkutilss {

    private static commonnetworkutilss instance = new commonnetworkutilss();
    static Context context;
    static ConnectivityManager connectivityManager;
    NetworkInfo wifiInfo, mobileInfo;
    static boolean connected = false;

    public static commonnetworkutilss getInstance(Context ctx) {
        context = ctx.getApplicationContext();
        return instance;
    }

    public static boolean isNetworkConnected() {
        try {
            connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() &&
                    networkInfo.isConnected();
            return connected;

        } catch (Exception e) {
            System.out.println("CheckConnectivity Exception: " + e.getMessage());
            Log.v("connectivity", e.toString());
        }
        return connected;
    }
}