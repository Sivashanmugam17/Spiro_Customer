package com.mauto.chd.ui.viewmodelforvehicllist

import android.content.Context
import com.mauto.chd.Modal.DefaultVehicleListModel
import com.mauto.chd.Modal.VehicleListModel
import com.mauto.chd.Modal.vehicleinfoedit
import com.mauto.chd.SessionManagerPackage.SessionManager
import com.mauto.chd.data.steptwodocumentdb.documenttwoDao
import com.mauto.chd.data.steptwodocumentdb.documenttwoDatabase
import com.mauto.chd.data.steptwodocumentdb.documenttwodoRecord
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import com.mauto.chd.R


class vehiclelistpagerepostiatry(private val listener: VehiclePageListener)
{

    var todoDao: documenttwoDao? = null


    //for getting driver details
    fun splitdata(context:Context,response:String)
    {
        try
        {
            var vehiclelisarray:ArrayList<VehicleListModel> = ArrayList()
            var responseObj = JSONObject(response)
            if (responseObj.getString("status").equals("1"))
            {
                var getingres: JSONObject? = responseObj.getJSONObject("response")
                val list = `getingres`!!.getJSONArray("vehicles")
                if (list.length() > 0)
                {
                    for (i in 0 until list.length())
                    {
                        var j_ride_object = list.getJSONObject(i)
                        if(j_ride_object!!.getString("is_default").equals("Yes"))
                        {
                            var vehicle_number = j_ride_object!!.getString("vehicle_number")
                            var vehicle_maker = j_ride_object!!.getString("vehicle_maker")
                            var vehicle_model = j_ride_object!!.getString("vehicle_model")
                            var is_default = j_ride_object!!.getString("is_default")
                            var verify_status = j_ride_object!!.getString("verify_status")
                            var vehicle_id = j_ride_object!!.getString("vehicle_id")
                            val vehiclepojo = DefaultVehicleListModel(vehicle_number,vehicle_maker,vehicle_model,is_default,verify_status,vehicle_id)
                            if (listener != null)
                            {
                                listener!!.defaultvehiclemodel(vehiclepojo)
                            }
                        }
                        else
                        {
                            var vehicle_number = j_ride_object!!.getString("vehicle_number")
                            var vehicle_maker = j_ride_object!!.getString("vehicle_maker")
                            var vehicle_model = j_ride_object!!.getString("vehicle_model")
                            var is_default = j_ride_object!!.getString("is_default")
                            var verify_status = j_ride_object!!.getString("verify_status")
                            var vehicle_id = j_ride_object!!.getString("vehicle_id")
                            val vehiclepojo = VehicleListModel(vehicle_number,vehicle_maker,vehicle_model,is_default,verify_status,vehicle_id)
                            vehiclelisarray.add(vehiclepojo)
                        }

                    }
                    if (listener != null)
                    {
                        listener!!.arraylistofvehiclemodel(vehiclelisarray)
                    }
                }
            }
            else
            {
                if (listener != null)
                {
                    if(responseObj.has("response"))
                        listener!!.errormessage(responseObj.getString("response"))
                    else
                        listener!!.errormessage(context.getString(R.string.failedproblem))
                }
            }
        }
        catch (e: JSONException)
        {
        }
    }
    //for getting driver details
    fun dataupdated(context:Context,response:String)
    {
        try
        {
            var responseObj = JSONObject(response)
            if (responseObj.getString("status").equals("1"))
            {
                if(responseObj.has("response"))
                    listener!!.successmessage(responseObj.getString("response"))
            }
            else
            {
                if (listener != null)
                {
                    if(responseObj.has("response"))
                        listener!!.errormessage(responseObj.getString("response"))
                    else
                        listener!!.errormessage(context.getString(R.string.failedproblem))
                }
            }
        }
        catch (e: JSONException)
        {
        }
    }
    //for getting driver details
    fun vehicleinfo(context:Context,response:String)
    {
        try
        {
            var responseObj = JSONObject(response)
            if (responseObj.getString("status").equals("1"))
            {
                if(responseObj.has("response"))
                {
                    val responseObj = responseObj.getJSONObject("response")
                    val vehicleinfo = responseObj.getJSONObject("vehicleinfo")

                    val vehicle_id = vehicleinfo.getString("vehicle_id")
                    val vehicle_number = vehicleinfo.getString("vehicle_number")

                    val vehicle_type = vehicleinfo.getJSONObject("type")
                    val typeid = vehicle_type.getString("id")
                    val typename = vehicle_type.getString("name")

                    val vehicle_make = vehicleinfo.getJSONObject("make")
                    val makeid = vehicle_make.getString("id")
                    val makename = vehicle_make.getString("name")

                    val vehicle_model = vehicleinfo.getJSONObject("model")
                    val modelid = vehicle_model.getString("id")
                    val modelname = vehicle_model.getString("name")

                    val year = vehicleinfo.getString("year")
                    val vehicleimage = vehicleinfo.getString("image")

                    val location = vehicleinfo.getJSONObject("location")
                    val locationid = location.getString("id")
                    val locationname = location.getString("name")

                    val category = vehicleinfo.getJSONObject("category")
                    val categoryid = category.getString("id")
                    val categoryname = category.getString("name")


                    var vehiclesinfo = vehicleinfoedit(vehicle_id,vehicle_number,typeid,typename,makeid,makename,modelid,modelname,year,vehicleimage,locationid,locationname,categoryid,categoryname)

                    if (listener != null)
                    {
                        var mSessionManager = SessionManager(context!!)

                        mSessionManager.setLocation(locationid,locationname)
                        mSessionManager.setCategory(categoryid,categoryname)
                        mSessionManager.setVehicleType(typeid,typename)
                        mSessionManager.setMake(makeid,makename)
                        mSessionManager.setModel(modelid,modelname)
                        mSessionManager.setVehicleImage(vehicleimage)
                        mSessionManager.setYear(year)

                        mSessionManager.settempvehicleid(vehicle_id)
                        mSessionManager.setvehiclenumber(vehicle_number)

                        listener!!.basicvehicleinfo(vehiclesinfo)
                    }
                }
            }
            else
            {
                if (listener != null)
                {
                    if(responseObj.has("response"))
                        listener!!.errormessage(responseObj.getString("response"))
                    else
                        listener!!.errormessage(context.getString(R.string.failedproblem))
                }
            }
        }
        catch (e: JSONException)
        {
        }
    }


    //for getting driver details
    fun documentinfo(context:Context,response:String)
    {
        try
        {
            var responseObj = JSONObject(response)
            if (responseObj.getString("status").equals("1"))
            {
                if(responseObj.has("response"))
                {
                    val responseObj = responseObj.getJSONObject("response")
                    val documents = responseObj.getJSONArray("documents")

                    val vehicleinfo = responseObj.getJSONObject("vehicleinfo")
                    val vehicle_id = vehicleinfo.getString("vehicle_id")

                    var mSessionManager = SessionManager(context!!)
                    mSessionManager.settempvehicleid(vehicle_id)

                    val database = documenttwoDatabase.getInstance(context.applicationContext)
                    todoDao = database!!.todoDao()

                    if (documents.length() > 0)
                    {
                        for (i in 0 until documents.length())
                        {
                            var j_ride_object = documents.getJSONObject(i)
                            var id: String       = j_ride_object.getString("id")
                            var name: String       = j_ride_object.getString("name")
                            var front: String       = j_ride_object.getString("front")
                            var back: String       = j_ride_object.getString("back")
                            var expiryDate: String       = j_ride_object.getString("expiryDate")
                            if(!expiryDate.equals(""))
                            {
                                var spf = SimpleDateFormat("yyyy-MM-dd")
                                val newDate: Date = spf.parse(expiryDate)
                                spf = SimpleDateFormat("dd/MM/yyyy")
                                expiryDate = spf.format(newDate)

                            }
                            var verify_status: String       = j_ride_object.getString("verify_status")
                            var uuid: String       = j_ride_object.getString("uuid")
                            var category: String       = j_ride_object.getString("category")
                            var is_req: String       = j_ride_object.getString("is_req")
                            var is_exp: String       = j_ride_object.getString("is_exp")
                            var has_front: String       = j_ride_object.getString("has_front")
                            var has_back: String       = j_ride_object.getString("has_back")
                            var has_input: String       = j_ride_object.getString("has_input")
                            var notes: String       = context.getString(R.string.notess)+documents.getJSONObject(i).getString("notes")

                            if(has_front.equals("1") && has_back.equals("1"))
                            {
                                var submittedsuccesfully = "0"
                                if(!front.equals("") && !back.equals(""))
                                {
                                    submittedsuccesfully = "1"
                                }

                                saveStepTwo(id,name,category,is_req,is_exp,notes,"3",submittedsuccesfully,front,back,expiryDate,uuid)
                            }
                            else if(has_front.equals("1") && has_back.equals("0"))
                            {
                                var submittedsuccesfully = "0"
                                if(!front.equals("") )
                                {
                                    submittedsuccesfully = "1"
                                }
                                saveStepTwo(id,name,category,is_req,is_exp,notes,"1",submittedsuccesfully,front,back,expiryDate,uuid)
                            }
                            else if(has_front.equals("0") && has_back.equals("1"))
                            {
                                var submittedsuccesfully = "0"
                                if(!back.equals("") )
                                {
                                    submittedsuccesfully = "1"
                                }
                                saveStepTwo(id,name,category,is_req,is_exp,notes,"2",submittedsuccesfully,front,back,expiryDate,uuid)
                            }
                            else
                            {
                                saveStepTwo(id,name,category,is_req,is_exp,notes,"0","0",front,back,expiryDate,uuid)
                            }



                        }
                        if (listener != null)   listener!!.documentadded(1)
                    }


                }
            }
            else
            {
                if (listener != null)
                {
                    if(responseObj.has("response"))
                        listener!!.errormessage(responseObj.getString("response"))
                    else
                        listener!!.errormessage(context.getString(R.string.failedproblem))
                }
            }
        }
        catch (e: JSONException)
        {
        }
    }


    fun saveStepTwo(id:String,name:String,category:String,is_req:String,is_exp:String,notes:String,fronorback:String,submittedfully:String,front:String,back:String,expiryDate:String,uuid:String)
    {
//        var todoRecord: documenttwodoRecord? = null
//        val comid = if (todoRecord != null) todoRecord?.id else null
//        val todo = documenttwodoRecord(id = comid, coloumnid = id, coloumnname = name,category = category,is_req = is_req,is_exp = is_exp,notes = notes,expirydate = expiryDate,imagestore = front,imagestore1 = back,uploadstatus = "",frontandback=fronorback,submitedfully = submittedfully,document_name = "",uuid = uuid)
//        saveTodo(todo)
    }

    fun saveTodo(todo: documenttwodoRecord) = runBlocking {
        this.launch(Dispatchers.IO) {
            todoDao!!.saveTwodoRecord(todo)
        }
    }
}