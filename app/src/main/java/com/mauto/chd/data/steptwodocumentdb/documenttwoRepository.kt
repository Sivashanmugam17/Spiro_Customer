package com.mauto.chd.data.steptwodocumentdb

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class documenttwoRepository(application: Application) {

    private val todoDao: documenttwoDao
    private val allTodos: LiveData<List<documenttwodoRecord>>



    init
    {
        val database = documenttwoDatabase.getInstance(application.applicationContext)
        todoDao = database!!.todoDao()
        allTodos = todoDao.getAllDocument()
    }


    fun deletallrecord() = runBlocking {
        this.launch(Dispatchers.IO) {
            todoDao.cleartablee()
        }
    }

    fun saveTodo(todo: documenttwodoRecord) = runBlocking {
        this.launch(Dispatchers.IO) {
            todoDao.saveTwodoRecord(todo)
        }
    }

    fun updateTodo(todo: documenttwodoRecord) = runBlocking {
        this.launch(Dispatchers.IO) {
            todoDao.updateTodo(todo)
        }
    }

    fun getAllTodoList(): LiveData<List<documenttwodoRecord>>
    {
        return allTodos
    }



}