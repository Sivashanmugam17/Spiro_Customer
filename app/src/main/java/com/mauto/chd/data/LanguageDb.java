package com.mauto.chd.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LanguageDb extends SQLiteOpenHelper {
    public static String DATABASE_NAME="userdatastrings";
    public static final String TABLE_NAME="strings";
    public static final String KEY_KEYTAG="keytag";
    public static final String KEY_VALUE="value";
    public static final String KEY_ID="id";
    Context ctx;
    private SQLiteDatabase dataBase;


    public LanguageDb(Context context) {
        super(context, DATABASE_NAME, null, 1);
        ctx =context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+" ("+KEY_ID+" INTEGER PRIMARY KEY, "+KEY_KEYTAG+" TEXT, "+KEY_VALUE+" TEXT)";
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public void saveData(String key,String value)
    {
        try {
            dataBase=getWritableDatabase();
            ContentValues values=new ContentValues();
            values.put(KEY_KEYTAG,key);
            values.put(KEY_VALUE,value);
            dataBase.insert(TABLE_NAME, null, values);
        }catch (Exception ex){
            System.out.println("db ex------");
            ex.printStackTrace();
        }
    }

    public void closedatabase()
    {
        //close database
        dataBase.close();
    }

    public String getvalueforkey(String Key)
    {
        String value = "";

       dataBase = getWritableDatabase();
        Cursor mCursor = dataBase.rawQuery("SELECT * FROM " + TABLE_NAME+" WHERE "+KEY_KEYTAG+"='"+Key+"'", null);
        if (mCursor.moveToFirst()) {
            do {
                value=mCursor.getString(mCursor.getColumnIndex(KEY_VALUE));
            } while (mCursor.moveToNext());
        }
        mCursor.close();
        if(value.equals(""))
            value = ctx.getResources().getString(
                    ctx.getResources().getIdentifier(Key, "string", ctx.getPackageName()));

        return value;
    }

}
