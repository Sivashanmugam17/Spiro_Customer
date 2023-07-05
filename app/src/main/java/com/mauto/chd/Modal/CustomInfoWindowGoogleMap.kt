package com.mauto.chd.Modal

import android.app.Activity
import android.content.Context
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.mauto.chd.R
import kotlinx.android.synthetic.main.layout_custom_map_marker.view.*

class CustomInfoWindowGoogleMap(val context: Context) : GoogleMap.InfoWindowAdapter {

    override fun getInfoContents(p0: Marker?): View? {

     return null
    }

    override fun getInfoWindow(p0: Marker?): View? {
        var mInfoView = (context as Activity).layoutInflater.inflate(R.layout.layout_custom_map_marker, null)
        var mInfoWindow: InfoWindowData? = p0?.tag as InfoWindowData?

        mInfoView.mins.text = mInfoWindow?.minsaway


        return mInfoView
    }
}