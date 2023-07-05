package com.mauto.chd.view_model_register_module


import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.mauto.chd.backgroundservices.commonapifetchservice
import com.mauto.chd.Modal.VehicleselectionModel
import com.mauto.chd.Modal.VehicleselectionModels
import com.mauto.chd.Modal.VehicleselectionModeltype
import com.mauto.chd.R
import com.mauto.chd.SessionManagerPackage.SessionManager
import org.json.JSONObject
import kotlin.collections.ArrayList


class VehicleDetailsViewModel(application: Application) : AndroidViewModel(application)
{
    lateinit var array: Array<String>
    private val responseLiveData = MutableLiveData<ArrayList<VehicleselectionModel>>()
    private val responseLiveDatas = MutableLiveData<ArrayList<VehicleselectionModels>>()
    private val responseLiveDatass = MutableLiveData<ArrayList<VehicleselectionModeltype>>()
    private val responseLiveDatasyear = MutableLiveData<ArrayList<String>>()


    private val filterresponseLiveData = MutableLiveData<ArrayList<VehicleselectionModel>>()
    private var mSessionManager: SessionManager? = null
    companion object{
        var myCategorytype : ArrayList<String> = ArrayList()
    }


    fun countryarrayobserver(): MutableLiveData<ArrayList<VehicleselectionModel>>
    {
        return responseLiveData
    }
    fun countryarrayobservers(): MutableLiveData<ArrayList<VehicleselectionModels>>
    {
        return responseLiveDatas
    }
    fun vechilemodelobservers(): MutableLiveData<ArrayList<VehicleselectionModeltype>>
    {
        return responseLiveDatass
    }
    fun yearmodelobservers(): MutableLiveData<ArrayList<String>>
    {
        return responseLiveDatasyear
    }


    fun filtercountryarrayobserver(): MutableLiveData<ArrayList<VehicleselectionModel>>
    {
        return filterresponseLiveData
    }
    fun filter(text: String,fullcountryarray: ArrayList<VehicleselectionModel>)
    {
        val filteredCourseAry: ArrayList<VehicleselectionModel> = ArrayList()
        val courseAry : ArrayList<VehicleselectionModel> = fullcountryarray
        for (eachCourse in courseAry)
        {
            if (eachCourse.name!!.toLowerCase().contains(text.toLowerCase()))
            {
                filteredCourseAry.add(eachCourse)
            }
        }
        filterresponseLiveData.value=filteredCourseAry
    }

    fun startcategoryfetchapi(mContext: Context)
    {
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.getmakemodelyear))
        mContext.startService(intent)
    }
    fun startcategoryapi(mContext: Context)
    {
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.getonlymake))
        mContext.startService(intent)
    }


    // get service location
    fun getservicelocation(mContext: Context)
    {
        val registerpagetwopage = Intent(mContext, commonapifetchservice::class.java)
        registerpagetwopage.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.getservicelocationresponse))
        mContext.startService(registerpagetwopage)
    }
    // get service location
    fun getvehiclemake(mContext: Context)
    {
        val registerpagetwopage = Intent(mContext, commonapifetchservice::class.java)
        registerpagetwopage.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.getonlymake))
        mContext.startService(registerpagetwopage)
    }


    fun fullcategoryresponse(mContext: Context,categoryid:String)
    {
        mSessionManager = SessionManager(mContext)
        categoryid
        try
        {
            var vehiclearraylist = ArrayList<VehicleselectionModels>()
            var vehiclearraylist1 = ArrayList<VehicleselectionModeltype>()
            var vehiclearraylist2 = ArrayList<String>()



            val response_json_object = JSONObject(mSessionManager!!.getCategoryDetails())
            try
            {
                val status = response_json_object.getString("status")
                if (status.equals("1"))
                {
                    var getingres: String = response_json_object.getString("response")
                    var resultsresponse = JSONObject(getingres)
                    var locations = resultsresponse.getJSONArray("vehicles")
                    for (i in 0 until locations.length()) {


                        var makeid = locations.getJSONObject(i).getString("id")
                        var makename = locations.getJSONObject(i).getString("name")
                        var models = locations.getJSONObject(i).getJSONArray("models")
                        vehiclearraylist.add(VehicleselectionModels(makeid, makename))
                        for (i in 0 until models.length()) {

                            var modelid: String = models.getJSONObject(i).getString("id")
                            var modelname: String = models.getJSONObject(i).getString("name")

                            var cat_idarray = models.getJSONObject(i).getJSONArray("years")
                            for (j in 0 until cat_idarray.length()) {
                                var cat_id: String = cat_idarray.getString(j).toString()
//                            if(categoryid.equals(cat_id))
//                            {
//                                vehiclearraylist.add(VehicleselectionModel(makeid, name,id,name,cat_idarray))
//                            }
                                vehiclearraylist2.add(cat_id)

                            }


                            vehiclearraylist1.add(VehicleselectionModeltype(modelid, modelname))

                        }
                    }

                    responseLiveDatas.value=vehiclearraylist
                    responseLiveDatass.value=vehiclearraylist1
                    responseLiveDatasyear.value=vehiclearraylist2
                }
            }
            catch (e: Exception)
            {
                Log.e("Exeception",e.toString())
            }
        }
        catch (e: Exception)
        {
            Log.e("Exeception1",e.toString())
        }

    }

    fun fullcategorymakeresponse(mContext: Context,vehicletypeid:String)
    {
        mSessionManager = SessionManager(mContext)
        try
        {
            var vehiclearraylist = ArrayList<VehicleselectionModel>()
            val response_json_object = JSONObject(mSessionManager!!.getCategoryDetails())
            try
            {
                val status = response_json_object.getString("status")
                if (status.equals("1"))
                {
                    var getingres: String = response_json_object.getString("response")
                    var resultsresponse = JSONObject(getingres)
                    var locations = resultsresponse.getJSONArray("vehicles")
                    for (i in 0 until locations.length())
                    {
                        var id: String = locations.getJSONObject(i).getString("id")
                        if(vehicletypeid.equals(id))
                        {
                            var makersarray = locations.getJSONObject(i).getJSONArray("makers")
                            for (j in 0 until makersarray.length())
                            {
                                var namemake: String = makersarray.getJSONObject(j).getString("name")
                                var idmake: String = makersarray.getJSONObject(j).getString("id")
                                vehiclearraylist.add(VehicleselectionModel(idmake, namemake))
                            }
                        }
                    }
                    responseLiveData.value=vehiclearraylist
                }
            }
            catch (e: Exception)
            {
            }
        }
        catch (e: Exception)
        {

        }

    }


    fun fullcategorymodelresponse(mContext: Context,makeid:String,categoryid:String)
    {
        mSessionManager = SessionManager(mContext)
        try
        {
            var vehiclearraylist = ArrayList<VehicleselectionModel>()
            val response_json_object = JSONObject(mSessionManager!!.getCategoryDetails())
            try
            {
                val status = response_json_object.getString("status")
                if (status.equals("1"))
                {
                    var getingres: String = response_json_object.getString("response")
                    var resultsresponse = JSONObject(getingres)
                    var locations = resultsresponse.getJSONArray("vehicles")
                    for (i in 0 until locations.length())
                    {
                        var cat_idarray = locations.getJSONObject(i).getJSONArray("cat_id")
                        for (j in 0 until cat_idarray.length())
                        {
                            var cat_id: String = cat_idarray.getString(j).toString()
                            if(categoryid.equals(cat_id))
                            {
                                var makersarray = locations.getJSONObject(i).getJSONArray("makers")
                                for (k in 0 until makersarray.length())
                                {
                                    var namemodel: String = makersarray.getJSONObject(k).getString("name")
                                    var idmodel: String = makersarray.getJSONObject(k).getString("id")
                                    if(makeid.equals(idmodel))
                                    {
                                        var modelsarray = makersarray.getJSONObject(k).getJSONArray("models")
                                        for (m in 0 until modelsarray.length())
                                        {
                                            var modelid: String = modelsarray.getJSONObject(m).getString("id")
                                            var modelname: String = modelsarray.getJSONObject(m).getString("name")
                                            vehiclearraylist.add(VehicleselectionModel(modelid, modelname))
                                        }
                                    }

                                }
                            }
                        }
                    }
                    responseLiveData.value=vehiclearraylist
                }
            }
            catch (e: Exception)
            {
            }
        }
        catch (e: Exception)
        {

        }

    }


    fun fullcategoryyearresponse(mContext: Context,makeid:String,categoryid:String,model_id:String)
    {
        mSessionManager = SessionManager(mContext)
        try
        {
            var vehiclearraylist = ArrayList<VehicleselectionModel>()
            val response_json_object = JSONObject(mSessionManager!!.getCategoryDetails())
            try
            {
                val status = response_json_object.getString("status")
                if (status.equals("1"))
                {
                    var getingres: String = response_json_object.getString("response")
                    var resultsresponse = JSONObject(getingres)
                    var locations = resultsresponse.getJSONArray("vehicles")
                    for (i in 0 until locations.length())
                    {
                        var cat_idarray = locations.getJSONObject(i).getJSONArray("cat_id")
                        for (j in 0 until cat_idarray.length())
                        {
                            var cat_id: String = cat_idarray.getString(j).toString()
                            if(categoryid.equals(cat_id))
                            {
                                var makersarray = locations.getJSONObject(i).getJSONArray("makers")
                                for (k in 0 until makersarray.length())
                                {
                                    var namemodel: String = makersarray.getJSONObject(k).getString("name")
                                    var idmodel: String = makersarray.getJSONObject(k).getString("id")
                                    if(makeid.equals(idmodel))
                                    {
                                        var modelsarray = makersarray.getJSONObject(k).getJSONArray("models")
                                        for (m in 0 until modelsarray.length())
                                        {
                                            var modelid: String = modelsarray.getJSONObject(m).getString("id")
                                            var modelname: String = modelsarray.getJSONObject(m).getString("name")
                                            if(modelid.equals(model_id))
                                            {
                                                var year = modelsarray.getJSONObject(m).getJSONArray("years")
                                                for (l in 0 until year.length())
                                                {
                                                    var yearstring: String = year.getString(l).toString()
                                                    vehiclearraylist.add(VehicleselectionModel(yearstring, yearstring))
                                                }

                                            }

                                        }
                                    }

                                }
                            }
                        }
                    }
                    responseLiveData.value=vehiclearraylist
                }
            }
            catch (e: Exception)
            {
            }
        }
        catch (e: Exception)
        {

        }

    }

    fun splilocationresponse(mContext:Context,finalresponse:String,skipingid:String)
    {
            var vehiclearraylist = ArrayList<VehicleselectionModel>()
            val response_json_object = JSONObject(finalresponse)
            try
            {
                val status = response_json_object.getString("status")
                if (status.equals("1"))
                {
                    var getingres: String = response_json_object.getString("response")
                    var resultsresponse = JSONObject(getingres)
//                    var locations = resultsresponse.getJSONArray("locations")
                    var locations = resultsresponse.getJSONArray("location_list")
                    for (i in 0 until locations.length())
                    {

                        var id: String = locations.getJSONObject(i).getString("id")
                        var city: String = locations.getJSONObject(i).getString("city")
                        val category = locations.getJSONObject(i).getJSONArray("category")
                        if (category.length() > 0){
                            for (i in 0 until category.length()){
                                var categorytype =category.getJSONObject(i).getString("category")
                                myCategorytype.add(categorytype)
                            }
                        }
                        vehiclearraylist.add(VehicleselectionModel(id, city,category))

                    }


                    responseLiveData.value=vehiclearraylist
                }
            }
            catch (e: Exception)
            {
                Log.d("dfdsgdfg", e.toString())
            }
    }


    fun splitresponse(mContext:Context,finalresponse:String,skipingid:String)
    {
        try
        {
            var vehiclearraylist = ArrayList<VehicleselectionModel>()
            val response_json_object = JSONObject(finalresponse)
            try
            {
                val status = response_json_object.getString("status")
                if (status.equals("1"))
                {
                    var getingres: String = response_json_object.getString("response")
                    var resultsresponse = JSONObject(getingres)
                    var locations = resultsresponse.getJSONArray("locations")
                    for (i in 0 until locations.length())
                    {

                        var id: String = locations.getJSONObject(i).getString("id")
                        var city: String = locations.getJSONObject(i).getString("city")
                        vehiclearraylist.add(VehicleselectionModel(id, city))
                        /*if(!skipingid.equals(id))
                        {
                            vehiclearraylist.add(VehicleselectionModel(id, city))
                        }*/

                    }
                    responseLiveData.value=vehiclearraylist
                }
            }
            catch (e: Exception)
            {
            }
        }
        catch (e: Exception)
        {

        }
    }

    fun splitresponsecategory(mContext:Context,finalresponse:String,locationid:String,skipingid:String)
    {
        try
        {
            var vehiclearraylist = ArrayList<VehicleselectionModel>()
            val response_json_object = JSONObject(finalresponse)
            try
            {
                val status = response_json_object.getString("status")
                if (status.equals("1"))
                {
                    var getingres: String = response_json_object.getString("response")
                    var resultsresponse = JSONObject(getingres)
                    var locations = resultsresponse.getJSONArray("locations")
                    for (i in 0 until locations.length())
                    {
                        var cityid: String = locations.getJSONObject(i).getString("id")
                        if(cityid.equals(locationid))
                        {
                            var category = locations.getJSONObject(i).getJSONArray("category")
                            for (j in 0 until category.length())
                            {
                                var idcategory: String = category.getJSONObject(j).getString("id")
                                var citycategory: String = category.getJSONObject(j).getString("category")
                                vehiclearraylist.add(VehicleselectionModel(idcategory, citycategory))
                                /* if(!skipingid.equals(idcategory))
                                 {
                                     vehiclearraylist.add(VehicleselectionModel(idcategory, citycategory))
                                 }*/

                            }
                        }
                    }
                    responseLiveData.value=vehiclearraylist
                }
            }
            catch (e: Exception)
            {
            }
        }
        catch (e: Exception)
        {

        }
    }
}

