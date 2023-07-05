package com.mauto.chd.mylocation

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.os.AsyncTask
import android.util.Log
import android.view.animation.LinearInterpolator
import com.mauto.chd.SessionManagerPackage.SessionManager
import com.mauto.chd.retrofit.RetrofitInstance
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.JointType.ROUND
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.gms.maps.model.SquareCap
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FindRoute(internal var routeInterface: RouteListeners) {

    private lateinit var mContext: Context
    private var mMap: GoogleMap? = null
    private var wayPoint = StringBuilder()
    var overViewPolyLine = ""
    private var blackPolyLine: Polyline? = null
    private var greyPolyLine: Polyline? = null
    private var listLatLng: List<LatLng>? = null
    private var pLat: Double = 0.0
    private var pLng: Double = 0.0
    private var dLat: Double = 0.0
    private var dLng: Double = 0.0
    private var wayPoints: ArrayList<LatLng> = ArrayList()
    private var approxFare: Double = 0.0
    private var isNeedToDrawRoute: Boolean = true
    private var requestedType = 0
    private var pickUp: LatLng? = null
    var mSessionManager: SessionManager? = null
    var google_key =""

    private var drop: LatLng? = null
    private var polyLineAnimationListener: Animator.AnimatorListener = object : Animator.AnimatorListener {
        override fun onAnimationStart(animator: Animator) {}
        override fun onAnimationEnd(animator: Animator) {
            val blackLatLng = blackPolyLine!!.points
            val greyLatLng = greyPolyLine!!.points
            greyLatLng.clear()
            greyLatLng.addAll(blackLatLng)
            blackLatLng.clear()
            blackPolyLine?.run {
                points = blackLatLng
                zIndex = 2f
            }
            greyPolyLine?.run {
                points = greyLatLng
            }
//            drawMarker()
        }

        override fun onAnimationCancel(animator: Animator) {
        }

        override fun onAnimationRepeat(animator: Animator) {
        }
    }

    /**
     * Entry point to draw route
     *
     * @param map
     * @param mcontext
     * @param source
     * @param destination
     */
    fun setUpPolyLine(map: GoogleMap, mcontext: Context, source: LatLng?, destination: LatLng?, points: ArrayList<LatLng>, isNeedToDrawRoute: Boolean) {
        this.mMap = map
        this.mContext = mcontext
        this.isNeedToDrawRoute = isNeedToDrawRoute
        this.pickUp = source
        this.drop = destination
        requestedType = 1
        if (source != null && destination != null)
            getGoogleRouteLog(source, destination, points).execute()
//            getRouteFromGoogle(source.latitude, source!!.longitude, destination!!.latitude, destination!!.longitude, points)
    }

    private inner class getGoogleRouteLog(source: LatLng, destination: LatLng, points: ArrayList<LatLng>) : AsyncTask<Void, Void, String>() {
        private val pLatitude: Double
        private val pLongitude: Double
        private var dLatitude: Double
        private var dLongitude: Double
        private var from = ""
        private var to = ""

        init {
            this.pLatitude = pickUp!!.latitude
            this.pLongitude = pickUp!!.longitude
            this.dLatitude = drop!!.latitude
            this.dLongitude = drop!!.longitude
            wayPoints = points
            from = "$pLatitude,$pLongitude"
            to = "$dLatitude,$dLongitude"
        }

        override fun doInBackground(vararg voids: Void): String? {
//            getRouteFromGoogle(pLatitude, pLongitude, dLatitude, dLongitude, wayPoints)
            getRoute(pLatitude, pLongitude, dLatitude, dLongitude, wayPoints)
            return ""
        }

        override fun onPostExecute(model: String?) {
            super.onPostExecute(model)


        }
    }

//    private fun getRouteFromGoogle(pLatitude: Double, pLongitude: Double, D_latitude: Double, D_longitude: Double, wayPoints: ArrayList<LatLng>) {
//        val wayPointsUrl = makeDirectionUrl(pLatitude, pLongitude, D_latitude, D_longitude, wayPoints)
//        val client: CoreClient = ServiceGenerator(mContext).createService(CoreClient::class.java)
//        val coreResponse = client.getPolylineDataWithWayPoint("https://maps.googleapis.com/maps/api/directions/json", "$pLatitude,$pLongitude",
//                "$D_latitude,$D_longitude", wayPointsUrl, "AIzaSyD_lpM4Zq2VVNf5nj_2_TK8xqThyfmqrUg")
//        try {
//            coreResponse.enqueue(object : Callback<ResponseBody> {
//                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                    if (response.isSuccessful && response.body() != null) {
//                        try {
//                            val gson = JSONObject(response.body()!!.string())
//                            if (!gson.getString("status").equals("OK", ignoreCase = true)) {
//                                setFailureDistance()
//                                if (gson.has("error_message")) {
//                                    val msg: String = gson.getString("error_message")
////                                ShowToast.center(mContext, msg)
//                                    return
//                                }
//                            } else {
//                                saveGoogleLog(pLatitude.toString() + "," + pLongitude + D_latitude + "," + D_longitude, gson.toString())
//                                if (isNeedToDrawRoute) {
//                                    drawRoutePolyline(parsePolylineFromPoints(gson))
//                                }
//                            }
//                        } catch (e: Exception) {
//                            e.printStackTrace()
//                            setFailureDistance()
////                        routeInterface.drawRoutePickToDrop(null, null, approxFare)
//                        }
//                    } else {
//                        setFailureDistance()
////                    routeInterface.drawRoutePickToDrop(null, null, approxFare)
//                    }
//                }
//
//                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                    t.printStackTrace()
//                    setFailureDistance()
////                routeInterface.drawRoutePickToDrop(null, null, approxFare)
//                }
//            })
//        }
//        catch (e:RuntimeException){
//            e.printStackTrace()
//        }
//
//    }

    private fun setFailureDistance() {

        routeInterface.routeDrawnPickToDrop(null, null)

    }

    /**
     * Get a list of latlng from polyline by decode
     *
     * @param jObject
     * @return
     */
    private fun parsePolylineFromPoints(jObject: JSONObject): List<LatLng> {
        var path: List<LatLng> = ArrayList()
        try {
            val jRoutes: JSONArray = jObject.getJSONArray("routes")
            /** Traversing all routes  */
            val jOverviewPoly: JSONObject = (jRoutes.get(0) as JSONObject).getJSONObject("overview_polyline")
            overViewPolyLine = jOverviewPoly.getString("points")
            path = decodePoly(overViewPolyLine)
        } catch (e1: JSONException) {
            e1.printStackTrace()
        }
        return path
    }
    private fun decodePoly(encodedPath: String): List<LatLng> {
        val len = encodedPath.length
        val path: ArrayList<LatLng> = ArrayList()
        var index = 0
        var lat = 0
        var lng = 0
        while (index < len) {
            var result = 1
            var shift = 0
            var b: Int
            do {
                b = encodedPath[index++].toInt() - 63 - 1
                result += b shl shift
                shift += 5
            } while (b >= 31)
            lat += if (result and 1 != 0) (result shr 1).inv() else result shr 1
            result = 1
            shift = 0
            do {
                b = encodedPath[index++].toInt() - 63 - 1
                result += b shl shift
                shift += 5
            } while (b >= 31)
            lng += if (result and 1 != 0) (result shr 1).inv() else result shr 1
            path.add(LatLng(lat.toDouble() * 1.0E-5, lng.toDouble() * 1.0E-5))
        }
        return path
    }

    public fun makeDirectionUrl(p_latitude: Double, p_longitude: Double, d_latitude: Double, d_longitude: Double, points: ArrayList<LatLng>?): String {
        wayPoint = StringBuilder()
        if (points != null) {
            if (points.size > 2) {
                pLat = points[0].latitude
                pLng = points[0].longitude
                dLat = points[points.size - 1].latitude
                dLng = points[points.size - 1].longitude
                for (i in 1 until points.size - 1) {
                    wayPoint.append(points[i].latitude)
                    wayPoint.append(',')
                    wayPoint.append(points[i].longitude)
                    if (i != points.size - 2) {
                        wayPoint.append("|")
                    }
                }
            } else {
                pLat = points[0].latitude
                pLng = points[0].longitude
                dLat = points[points.size - 1].latitude
                dLng = points[points.size - 1].longitude
            }
        } else {
            pLat = p_latitude
            pLng = p_longitude
            dLat = d_latitude
            dLng = d_longitude
        }
        return wayPoint.toString()
    }

    internal fun drawRoutePolyline(result: List<LatLng>) {

        val lineOptions = PolylineOptions()
        listLatLng = ArrayList()
        this.listLatLng = result
        if (mMap != null) {
            lineOptions.apply {
                width(8f)
                color(Color.BLACK)
                startCap(SquareCap())
                endCap(SquareCap())
                jointType(ROUND)
            }
            blackPolyLine = mMap?.run {
                addPolyline(lineOptions)
            }
            val greyOptions = PolylineOptions().apply {
                width(8f)
                color(Color.BLACK)
                startCap(SquareCap())
                endCap(SquareCap())
                jointType(ROUND)
            }
            greyPolyLine = mMap?.run {
                addPolyline(greyOptions)
            }
            animatePolyLine(1000)
        }


    }
    fun clearpolyline(){
        blackPolyLine?.remove()
        greyPolyLine?.remove()
//        mMap?.clear()
    }


    private fun animatePolyLine(durations: Long) {
        val animator = ValueAnimator.ofInt(0, 100)
        animator.apply {
            duration = durations
            interpolator = LinearInterpolator()
        }
        animator.addUpdateListener { animator ->
            val latLngList = blackPolyLine!!.points
            val initialPointSize = latLngList.size
            val animatedValue = animator.animatedValue as Int
            val newPoints = animatedValue * listLatLng!!.size / 100

            if (initialPointSize < newPoints) {
                latLngList.addAll(listLatLng!!.subList(initialPointSize, newPoints))
                blackPolyLine!!.points = latLngList
            }
        }
        animator.addListener(polyLineAnimationListener)
        animator.start()
    }

    /* private fun drawMarker() {
         if (wayPoints.size > 2) {
             for (i in 1 until wayPoints.size - 1) {
                 mMap!!.addMarker(MarkerOptions()
                         .position(wayPoints[i])
                         .icon(BitmapDescriptorFactory.fromResource(R.drawable.black_logo)))
             }
         }
     }*/

    private fun saveGoogleLog(latLngKey: String, routeResult: String) {
        var time = 0.0
        var distance = 0.0
        try {
            val legsArray = JSONObject(routeResult).getJSONArray("routes").getJSONObject(0).getJSONArray("legs")
            if (legsArray != null) {
                for (i in 0 until legsArray.length()) {
                    val distanceObject = legsArray.getJSONObject(i).getJSONObject("distance")
                    val distanceString = distanceObject.getString("value")
                    if (distanceString != null && distanceString.isNotEmpty()) {
                        distance += java.lang.Double.parseDouble(distanceString)
                    }
                    val timeObject = legsArray.getJSONObject(i).getJSONObject("duration")
                    val timeString = timeObject.getString("value")
                    if (timeString != null && timeString.isNotEmpty()) {
                        time += java.lang.Double.parseDouble(timeString)
                    }
                }
            }
            val approxTravelTime = time / 60
            var approxTravelDist = distance / 1000

            Log.d("dew455545", approxTravelTime.toString() + "" + approxTravelDist)


            routeInterface.routeDrawnPickToDrop(approxTravelTime, approxTravelDist)
        } catch (e: JSONException) {
            e.printStackTrace()

        }
    }
    fun getRoute(pLatitude: Double, pLongitude: Double, D_latitude: Double, D_longitude: Double, wayPoints: ArrayList<LatLng>){
        //        heroList = new DocumentListsItem();
        mSessionManager = SessionManager(mContext)
        google_key= mSessionManager!!.getgoogle_api_key()!!
        Log.d("dfftgfgfg",google_key)

        val wayPointsUrl = makeDirectionUrl(pLatitude, pLongitude, D_latitude, D_longitude, wayPoints)

        val responseCall = RetrofitInstance.getGoogleInstance()
        val api_key =  google_key

        var call: Call<ResponseBody> = responseCall.getPolylineDataWithWayPoint("https://maps.googleapis.com/maps/api/directions/json", "$pLatitude,$pLongitude",
                "$D_latitude,$D_longitude", wayPointsUrl, api_key)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful && response.body() != null) {
                    try {
                        val gson = JSONObject(response.body()!!.string())
                        if (!gson.getString("status").equals("OK", ignoreCase = true)) {
                            setFailureDistance()
                            if (gson.has("error_message")) {
                                val msg: String = gson.getString("error_message")
//                                ShowToast.center(mContext, msg)
                                return
                            }
                        } else {
                            saveGoogleLog(pLatitude.toString() + "," + pLongitude + D_latitude + "," + D_longitude, gson.toString())
                            if (isNeedToDrawRoute) {
                                drawRoutePolyline(parsePolylineFromPoints(gson))
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        setFailureDistance()
//                        routeInterface.drawRoutePickToDrop(null, null, approxFare)
                    }
                } else {
                    setFailureDistance()
//                    routeInterface.drawRoutePickToDrop(null, null, approxFare)
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                println("Error message$t")
            }
        })
    }

    fun remove() {
        blackPolyLine!!.remove()
    }
}