package com.mauto.chd.clickableInterface

import android.view.View

interface CustomOnClickListener {
    fun onItemClickListener(view: View, position: Int,code:String,codeshortname:String)
}