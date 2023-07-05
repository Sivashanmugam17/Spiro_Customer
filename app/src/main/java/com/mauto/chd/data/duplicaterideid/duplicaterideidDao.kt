package com.mauto.chd.data.duplicaterideid

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface duplicaterideidDao
{
    @Insert
    suspend fun saveTwodoRecord(twodoRecord: duplicaterideidRecord)

    @Update
    suspend fun updateTodo(todoRecord: duplicaterideidRecord)

    @Query("SELECT * FROM duplicaterideidreqused WHERE rideid IN(:rideid) AND  timestamp IN(:timestamp)")
    fun getAllDocument(rideid:String,timestamp:String): List<duplicaterideidRecord>
}