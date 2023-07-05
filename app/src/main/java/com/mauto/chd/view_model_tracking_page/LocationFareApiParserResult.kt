package com.mauto.chd.view_model_tracking_page

import com.google.android.gms.maps.model.LatLng
import java.io.Serializable

data class LocationFareApiParserResult (
        var isInBoundary : ArrayList<Boolean>?,
        var category_item_array : ArrayList<CategoryItemModel>?,
        var category_item_updated_array : ArrayList<CategoryItemModel>?,
        var currency_symbol : String?,
        var boundary_lat_lng_array : ArrayList<LatLng>?,
        var estimated_price_array : ArrayList<String>?,
        var isServiceAvail : Boolean?,
        var current_boundary_name : String
):Serializable
