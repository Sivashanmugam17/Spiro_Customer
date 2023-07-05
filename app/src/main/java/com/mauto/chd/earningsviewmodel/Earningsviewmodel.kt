package com.mauto.chd.earningsviewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData


class Earningsviewmodel(application: Application) : AndroidViewModel(application)
{
    private val close = MutableLiveData<Int>()
    fun closeobserver(): MutableLiveData<Int>
    {
        return close
    }
    fun closeclick()
    {
        close.value = 1
    }

}