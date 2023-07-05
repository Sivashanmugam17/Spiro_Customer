package com.mauto.chd.clickableInterface

import android.view.View

interface CustomOnClickListenerForDocument {
    fun onItemClickListener(view: View, position: Int,code:String,codeshortname:String,name:String,Type:String,verfiystatus:String)
}