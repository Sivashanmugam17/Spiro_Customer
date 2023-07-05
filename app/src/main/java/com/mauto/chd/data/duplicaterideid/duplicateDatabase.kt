package com.mauto.chd.data.duplicaterideid

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase



@Database(entities = [duplicaterideidRecord::class], version = 1, exportSchema = false)
abstract class duplicateDatabase : RoomDatabase()
{
    abstract fun todoDao(): duplicaterideidDao
    companion object
    {
        private var INSTANCE: duplicateDatabase? = null
        fun getInstance(context: Context): duplicateDatabase?
        {
            if (INSTANCE == null)
            {
                synchronized(duplicateDatabase::class)
                {
                    INSTANCE = Room.databaseBuilder(context,
                            duplicateDatabase::class.java,
                            "duplicaterideidreqused_db")
                            .build()
                }
            }
            return INSTANCE
        }
    }
}