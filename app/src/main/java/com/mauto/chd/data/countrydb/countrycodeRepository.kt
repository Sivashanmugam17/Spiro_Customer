package com.mauto.chd.data.countrydb

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class countrycodeRepository(application: Application) {

    private val todoDao: countryDao
    private val allTodos: LiveData<List<CountrydoRecord>>

    init
    {
        val database = countryDatabase.getInstance(application.applicationContext)
        todoDao = database!!.todoDao()
        allTodos = todoDao.getAllCountryFlag()
    }


    fun saveTodo(todo: CountrydoRecord) = runBlocking {
        this.launch(Dispatchers.IO) {
            todoDao.saveTodo(todo)
        }
    }

    fun getAllTodoList(): LiveData<List<CountrydoRecord>>
    {
        return allTodos
    }
}