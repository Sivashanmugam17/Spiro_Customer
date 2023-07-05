package com.mauto.chd.view_model_with_repositary_main

import android.content.Context
import com.mauto.chd.Modal.DriverBasicData
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
import com.mauto.chd.R


class editpagerepostiatry(private val listener: editListener)
{

    var todoDao: documenttwoDao? = null

    //for getting driver details
    fun getbasicdriverdetails(mcontext:Context)
    {
        var mSessionManager  = SessionManager(mcontext)
        val driver_id = mSessionManager.getUserDetails()!![mSessionManager!!.id]!!
        val driver_image =mSessionManager!!.getUserDetails()!![mSessionManager!!.driver_image]!!
        val first_name =mSessionManager!!.getUserDetails()!![mSessionManager!!.first_name]!!
        val last_name =mSessionManager!!.getUserDetails()!![mSessionManager!!.last_name]!!
        val email =mSessionManager!!.getUserDetails()!![mSessionManager!!.email]!!
        val gender =mSessionManager!!.getUserDetails()!![mSessionManager!!.gender]!!
        val dob =mSessionManager!!.getUserDetails()!![mSessionManager!!.dob]!!
        var mobilecode =mSessionManager!!.getUserDetails()!![mSessionManager!!.mobilecode]!!
        if(!mobilecode.startsWith("+"))
        {
            mobilecode = "+$mobilecode"
        }
        val mobilenumber =mSessionManager!!.getUserDetails()!![mSessionManager!!.mobilenumber]!!
        val address =mSessionManager!!.getUserDetails()!![mSessionManager!!.address]!!
        val countryid =mSessionManager!!.getUserDetails()!![mSessionManager!!.countryid]!!
        val state =mSessionManager!!.getUserDetails()!![mSessionManager!!.state]!!
        val city =mSessionManager!!.getUserDetails()!![mSessionManager!!.city]!!
        val zipcode =mSessionManager!!.getUserDetails()!![mSessionManager!!.zipcode]!!


        val basicdata = DriverBasicData(driver_id,driver_image,first_name,last_name,email,dob,gender,address,countryid,state,city,zipcode,mobilecode,mobilenumber)

        listener!!.onUserDataReceived(basicdata)
    }


    //for getting driver details
    fun getdrivermobile(mcontext:Context)
    {
        var mSessionManager  = SessionManager(mcontext)
        var mobilecode =mSessionManager!!.getUserDetails()!![mSessionManager!!.mobilecode]!!
        if(!mobilecode.startsWith("+"))
        {
            mobilecode = "+$mobilecode"
        }
        val mobilenumber =mSessionManager!!.getUserDetails()!![mSessionManager!!.mobilenumber]!!


        listener!!.updatemobileno(mobilecode+" "+mobilenumber)
    }
    //for getting driver details
    fun getemaiidd(mcontext:Context)
    {
        var mSessionManager  = SessionManager(mcontext)
        var email =mSessionManager!!.getUserDetails()!![mSessionManager!!.email]!!

        listener!!.uodatemailid(email)
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


                    var mSessionManager = SessionManager(context!!)
//                    mSessionManager.settempvehicleid("")

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