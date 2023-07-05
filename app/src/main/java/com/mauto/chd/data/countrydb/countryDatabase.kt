package com.mauto.chd.data.countrydb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase



@Database(entities = [CountrydoRecord::class], version = 1, exportSchema = false)
abstract class countryDatabase : RoomDatabase()
{
    abstract fun todoDao(): countryDao
    companion object
    {
        private var INSTANCE: countryDatabase? = null
        fun getInstance(context: Context): countryDatabase?
        {
            if (INSTANCE == null)
            {
                synchronized(countryDatabase::class)
                {
                    INSTANCE = Room.databaseBuilder(context,
                            countryDatabase::class.java,
                            "countryflag_db")
                            .build()
                }
            }
            return INSTANCE
        }
    }
}