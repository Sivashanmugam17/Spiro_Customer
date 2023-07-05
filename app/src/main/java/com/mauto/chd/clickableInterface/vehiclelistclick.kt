package com.mauto.chd.clickableInterface

import android.view.View


interface vehiclelistclick {
    fun onRecyclerViewItemClick(view: View,vehicle_maker:String, vehicle_id: String, type: String)
}