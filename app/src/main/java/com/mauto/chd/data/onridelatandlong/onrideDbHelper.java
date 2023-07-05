package com.mauto.chd.data.onridelatandlong;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class onrideDbHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME="onridedatawtbase";
    public static final String TABLE_NAME="onridetawblename";
    public static final String TABLE_NAME_FOR_OTP="latlong_data_for_otp_onride";
    public static final String TABLE_NAME_FOR_NORMAL_RIDE="latlong_data_for_normal_onride";

    public static final String rideid="rideid";
    public static final String latitude="latitude";
    public static final String longitude="longitude";
    public static final String km="km";
    public static final String time="time";
    public static final String stattus="stattus";

    public static final String KEY_ID="id";

    public onrideDbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+" ("+KEY_ID+" INTEGER PRIMARY KEY, "+rideid+" TEXT, "+latitude+" TEXT, "+longitude+" TEXT, "+km+" TEXT, "+time+" TEXT, "+stattus+" TEXT)";
        String CREATE_TABLE_OTP="CREATE TABLE "+TABLE_NAME_FOR_OTP+" ("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+latitude+" TEXT, "+longitude+" TEXT, "+time+" TEXT)";
        String CREATE_TABLE_NORMAL="CREATE TABLE "+TABLE_NAME_FOR_NORMAL_RIDE+" ("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+latitude+" TEXT, "+longitude+" TEXT, "+time+" TEXT)";

        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TABLE_OTP);
        db.execSQL(CREATE_TABLE_NORMAL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_FOR_OTP);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_FOR_NORMAL_RIDE);

        onCreate(db);
    }

    public int getProfilesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public Cursor getLatLongForOtpRide() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME_FOR_OTP;
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(countQuery, null);
    }

    public Cursor getLatLongFornormalRide() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME_FOR_NORMAL_RIDE;
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(countQuery, null);
    }

//    public ArrayList<LatLng> getDataDistance(String status) {
//        ArrayList<LatLng> endDistanceTrips = new ArrayList<LatLng>();
//        try {
//            String rideIDStatic = "";
//            String selectQuery = "SELECT  * FROM " + LATLONG_TABLE_DISTANCE + " WHERE status = " + status;
//            System.out.println("query-----jai" + selectQuery);
//            SQLiteDatabase db =this.getReadableDatabase();
//
//            if (db.isOpen()) {
//                Cursor cursor = db.rawQuery(selectQuery, null);
//                String[] data = null;
//                if (cursor.moveToFirst()) {
//                    do {
//                        String rideid = cursor.getString(0);
//                        String geolat = cursor.getString(1);
//                        String geolong = cursor.getString(2);
//                        String geo_time = cursor.getString(3);
//                        System.out.println("Driver time Lat Long ---------------------" + geo_time + "-------------------" + geolat + "----------------" + geolong);
//                        endDistanceTrips.add(new LatLng(Double.parseDouble(geolat), Double.parseDouble(geolong)));
//                    } while (cursor.moveToNext());
//                }
//                //     db.close();
//                System.out.println("distance jai latlong " + endDistanceTrips.toString());
//            }
//        } catch (android.database.SQLException e) {
//            e.printStackTrace();
//        }
//        return endDistanceTrips;
//
//    }

//public void clearData(){
//    db=this.onrideDbHelper
//    db.execSQL("delete from "+ TABLENAME_ReceivedPackageRes);
//}
}