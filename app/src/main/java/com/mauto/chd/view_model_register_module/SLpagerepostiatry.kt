package com.mauto.chd.view_model_register_module

import com.mauto.chd.Modal.ServiceLocationModel
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class SLpagerepostiatry(private val listener: ServiceLocationListener)
{
    //for getting location details
    fun getlocationvalue(response:String)
    {
        try
        {
            var slarraylist = ArrayList<ServiceLocationModel>()
            var responseObj = JSONObject(response)
            if (responseObj.getString("status").equals("1"))
            {
                if (responseObj.has("response"))
                {
                    var getingres: String = responseObj.getString("response")
                    var resultsresponse = JSONObject(getingres)

                    var locations = resultsresponse.getJSONArray("locations")
                    for (i in 0 until locations.length())
                    {
                        var id = locations.getJSONObject(i).getString("id")
                        var city = locations.getJSONObject(i).getString("city")
                        slarraylist.add(ServiceLocationModel(id,city))
                    }

                }
                if (listener != null)
                {
                    listener!!.onDataReceived(slarraylist)
                }
            }
        }
        catch (e: JSONException)
        {
        }
    }




}