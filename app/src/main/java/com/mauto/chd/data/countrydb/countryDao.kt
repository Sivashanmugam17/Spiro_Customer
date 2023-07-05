package com.mauto.chd.data.countrydb

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface countryDao {

    @Insert
    suspend fun saveTodo(todoRecord: CountrydoRecord)

    @Query("SELECT * FROM countryflag ORDER BY id DESC")
    fun getAllCountryFlag(): LiveData<List<CountrydoRecord>>
}