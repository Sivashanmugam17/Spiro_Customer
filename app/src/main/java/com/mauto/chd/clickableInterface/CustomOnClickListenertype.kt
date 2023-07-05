package com.mauto.chd.clickableInterface

import android.view.View

 interface CustomOnClickListenertype {
     fun onItemClickListener(view: View, position: Int)
}
interface CustomOnClickListenerNew {
    fun onItemClickListener(view: View, position: Int, categoryId : String, coupenCode : String)
}
