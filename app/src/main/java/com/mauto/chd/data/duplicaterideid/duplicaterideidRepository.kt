package com.mauto.chd.data.duplicaterideid

import android.app.Application
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class duplicaterideidRepository(application: Application,ride_id:String,timestamp:String) {

    private val todoDao: duplicaterideidDao
    private val allTodos: List<duplicaterideidRecord>


    init
    {
        val database = duplicateDatabase.getInstance(application.applicationContext)
        todoDao = database!!.todoDao()
        allTodos = todoDao.getAllDocument(ride_id,timestamp)
    }

    fun saveTodo(todo: duplicaterideidRecord) = runBlocking {
        this.launch(Dispatchers.IO) {
            todoDao.saveTwodoRecord(todo)
        }
    }


    fun getAllTodoList():List<duplicaterideidRecord>
    {
        return allTodos
    }



}