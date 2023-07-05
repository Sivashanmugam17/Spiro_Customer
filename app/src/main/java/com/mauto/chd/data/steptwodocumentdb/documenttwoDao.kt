package com.mauto.chd.data.steptwodocumentdb

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface documenttwoDao
{
    @Insert
    suspend fun saveTwodoRecord(twodoRecord: documenttwodoRecord)

    @Update
    suspend fun updateTodo(todoRecord: documenttwodoRecord)

    @Query("SELECT * FROM steptwodocument ORDER BY id DESC")
    fun getAllDocument(): LiveData<List<documenttwodoRecord>>

    @Query("DELETE FROM steptwodocument")
    fun cleartablee()

    @Query("SELECT * FROM steptwodocument WHERE coloumnid IN (:coloumnids)")
    fun getparticularAllDocument(coloumnids: String): List<documenttwodoRecord>
}