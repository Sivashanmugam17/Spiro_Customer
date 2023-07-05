package com.mauto.chd.data.chatdb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME="userdata";
    public static final String TABLE_NAME="user";

    public static final String rideid="rideid";
    public static final String message="message";
    public static final String senderid="senderid";
    public static final String timestamp="timestamp";
    public static final String status="status";

    public static final String KEY_ID="id";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+" ("+KEY_ID+" INTEGER PRIMARY KEY, "+rideid+" TEXT, "+message+" TEXT, "+senderid+" TEXT, "+timestamp+" TEXT, "+status+" TEXT)";
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }

}