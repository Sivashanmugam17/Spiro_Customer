package com.mauto.chd.data.steptwodocumentdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase



@Database(entities = [documenttwodoRecord::class], version = 1, exportSchema = false)
abstract class documenttwoDatabase : RoomDatabase()
{
    abstract fun todoDao(): documenttwoDao
    companion object
    {
        private var INSTANCE: documenttwoDatabase? = null
        fun getInstance(context: Context): documenttwoDatabase?
        {
            if (INSTANCE == null)
            {
                synchronized(documenttwoDatabase::class)
                {
                    INSTANCE = Room.databaseBuilder(context,
                            documenttwoDatabase::class.java,
                            "steptwodocument_db")
                            .build()
                }
            }
            return INSTANCE
        }
    }
}