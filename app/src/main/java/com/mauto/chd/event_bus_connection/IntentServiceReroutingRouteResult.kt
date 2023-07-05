package com.mauto.chd.event_bus_connection

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.PolylineOptions

class IntentServiceReroutingRouteResult internal constructor(resultCode: Int, resultValue: PolylineOptions, wayPointsBuilder: LatLngBounds.Builder, routeLatLngArray: ArrayList<LatLng>) {

    var result: Int = 0
        internal set
    var resultValue: PolylineOptions
        internal set
    var wayPoints: LatLngBounds.Builder
        internal set
    var routeLatLngArray: ArrayList<LatLng>
        internal set

    init {
        result = resultCode
        this.resultValue = resultValue
        this.wayPoints = wayPointsBuilder
        this.routeLatLngArray = routeLatLngArray
    }
}