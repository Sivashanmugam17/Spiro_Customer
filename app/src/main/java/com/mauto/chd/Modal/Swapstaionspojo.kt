package com.mauto.chd.Modal

import com.google.gson.annotations.SerializedName

data class Swapstaionspojo(

	@field:SerializedName("response")
	val response: Response? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class StationLocation(

	@field:SerializedName("lng")
	val lng: Double? = null,

	@field:SerializedName("lat")
	val lat: Double? = null
)

data class Response(

	@field:SerializedName("station")
	val station: List<StationItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class StationItem(

	@field:SerializedName("station_name")
	val stationName: String? = null,

	@field:SerializedName("avail_batteries")
	val availBatteries: String? = null,

	@field:SerializedName("station_address")
	val stationAddress: String? = null,

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("cm")
	val commando: String? = null,

	@field:SerializedName("ch")
	val chap: String? = null,

	@field:SerializedName("pm")
	val piam: String? = null,

	@field:SerializedName("me")
	val meletric: String? = null,


	@field:SerializedName("station_location")
	val stationLocation: StationLocation? = null



)
