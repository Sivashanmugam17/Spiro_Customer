package com.mauto.chd.view_model_ride_request

class ItemInfo(var rideid: String?, var timestamp: String?, private var category: String?, private var timer: Long?, private var rating: String?, var min: String?, var miles: String?, var droppoint: String?, var pickuplat: String?, var pickuplong: String?, private var countdown: Long?, private var endTime: Long?,var pickup: String?) {

    fun getrideid(): String?
    {
        return rideid
    }

    fun getpickup(): String?
    {
        return pickup
    }

    fun gettimestamp(): String?
    {
        return timestamp
    }
    fun gettimer(): Long?
    {
        return timer
    }
    fun getcategory(): String?
    {
        return category
    }
    fun getrating(): String?
    {
        return rating
    }
    fun getmin(): String?
    {
        return min
    }
    fun getmiles(): String?
    {
        return miles
    }
    fun getdroppoint(): String?
    {
        return droppoint
    }
    fun getpickuplat(): String?
    {
        return pickuplat
    }
    fun getpickuplong(): String?
    {
        return pickuplong
    }

    fun getcountdowntime(): Long?
    {
        return countdown
    }
    fun getendtime(): Long?
    {
        return endTime
    }




    fun setrideid(rideid: String)
    {
        this.rideid = rideid
    }

    fun setpickup(pickup: String)
    {
        this.pickup = pickup
    }

    fun settimestamp(timestamp: String)
    {
        this.timestamp = timestamp
    }
    fun settimer(timer: Long)
    {
        this.timer = timer
    }
    fun setcategory(category: String)
    {
        this.category = category
    }
    fun setrating(rating: String)
    {
        this.rating = rating
    }
    fun setmin(min: String)
    {
        this.min = min
    }
    fun setmiles(miles: String)
    {
        this.miles = miles
    }
    fun setdroppoint(droppoint: String)
    {
        this.droppoint = droppoint
    }
    fun setpickuplat(pickuplat: String)
    {
        this.pickuplat = pickuplat
    }
    fun setpickuplong(pickuplong: String)
    {
        this.pickuplong = pickuplong
    }
    fun setCountdowntime(countdowntime: Long)
    {
        this.countdown = countdowntime
    }
    fun setEndtime(endtime: Long)
    {
        this.endTime = endtime
    }
}