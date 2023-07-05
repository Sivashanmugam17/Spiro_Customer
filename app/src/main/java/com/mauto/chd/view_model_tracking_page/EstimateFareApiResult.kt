package com.mauto.chd.view_model_tracking_page

import com.google.android.gms.maps.model.LatLng
import java.io.Serializable

//data class EstimateFareApiResult (
//        var isInBoundary: ArrayList<Boolean>?,
//        var boundary_lat_lng_array : ArrayList<LatLng>?,
//        var estimated_price_array : ArrayList<String>?,
//        var min_fare_array : ArrayList<Int>?,
//        var c_pool_fare_breakup_array : ArrayList<SharePoolFareModel>?,
//        var cPoolAvail : Boolean,
//        var share_pool_double_seat_value : Double?,
//        var share_pool_cate_postion : Int?,
//        var share_pool_single_seat_value : Double,
//        var c_pool_min_value : Int
//):Serializable
//
data class EstimateFareApiResult (
        var isInBoundary: ArrayList<Boolean>?,
        var boundary_lat_lng_array : ArrayList<LatLng>?,
        var estimated_price_array : ArrayList<String>?,
        var min_fare_array : ArrayList<Int>?,
        var c_pool_fare_breakup_array : ArrayList<SharePoolFareModel>?,
        var cPoolAvail : Boolean,
        var share_pool_double_seat_value : Double?,
        var share_pool_cate_postion : Int?,
        var share_pool_single_seat_value : Double,
        var c_pool_min_value : Int
):Serializable