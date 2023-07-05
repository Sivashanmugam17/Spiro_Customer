package com.mauto.chd.ui.sidemenus


import android.app.Application
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.mauto.chd.backgroundservices.commonapifetchservice
import com.mauto.chd.Modal.DriverBasicData
import com.mauto.chd.R
import com.mauto.chd.SessionManagerPackage.SessionManager
import com.mauto.chd.view_model_with_repositary_main.editListener
import com.mauto.chd.view_model_with_repositary_main.editpagerepostiatry
import com.mauto.chd.data.steptwodocumentdb.documenttwoRepository
import com.mauto.chd.ui.registeration.EightFoldsDatePickerDialog
//import com.developers.imagezipper.ImageZipper
//import fr.castorflex.android.smoothprogressbar.SmoothProgressBar
import java.util.*


class editprofileViewModel(application: Application) :  AndroidViewModel(application)
{
    private val movenext = MutableLiveData<Int>()
    private val countrycodestringemit = MutableLiveData<String>()
    private val errormessage = MutableLiveData<String>()
    private val countryselected = MutableLiveData<String>()
    private val countryselectedwithcode = MutableLiveData<String>()
    private val imageonbase64 = MutableLiveData<String>()
    private val successmo = MutableLiveData<String>()
    private val dobdate = MutableLiveData<String>()
    private val senddobdate = MutableLiveData<String>()
    private var mSessionManager: SessionManager? = null
    private lateinit var base64string: String
    private val updatemobilenos = MutableLiveData<String>()
    private val updateemailid = MutableLiveData<String>()



    var cal = Calendar.getInstance()
    private val repository: documenttwoRepository = documenttwoRepository(application)

    private val driverdetails = MutableLiveData<DriverBasicData>()

    var dashboarddata: editpagerepostiatry


    fun countrycodeobservervalue(): MutableLiveData<String>
    {
        return countrycodestringemit
    }
    fun updatemailidobserver(): MutableLiveData<String>
    {
        return updateemailid
    }

    fun updatemobilenoobserver(): MutableLiveData<String>
    {
        return updatemobilenos
    }

    fun errormessageobserver(): MutableLiveData<String>
    {
        return errormessage
    }

    fun countryselectedobservervalue(): MutableLiveData<String>
    {
        return countryselected
    }
    fun countryselectedwithcodeobservervalue(): MutableLiveData<String>
    {
        return countryselectedwithcode
    }
    fun movenextobserver(): MutableLiveData<Int>
    {
        return movenext
    }



    fun success(): MutableLiveData<String>
    {
        return successmo
    }


    fun imagebase64observervalue(): MutableLiveData<String>
    {
        return imageonbase64
    }
    fun getdriverbasicdetailsobserver(): MutableLiveData<DriverBasicData>
    {
        return driverdetails
    }
    fun dobdateobserver(): MutableLiveData<String>
    {
        return dobdate
    }
    fun dobdateobserver1(): MutableLiveData<String>
    {
        return senddobdate
    }

    init
    {
        dashboarddata = editpagerepostiatry( object : editListener
        {
            override fun onUserDataReceived(mutableLiveData: DriverBasicData) {
                driverdetails.value = mutableLiveData
            }

            override fun errormessage(mutuablelivedata: String) {
                errormessage.value = mutuablelivedata
            }

            override fun documentadded(mutuablelivedata: Int) {
                movenext.value = mutuablelivedata
            }

            override fun updatemobileno(mutuablelivedata: String) {
                updatemobilenos.value = mutuablelivedata
            }

            override fun uodatemailid(mutuablelivedata: String) {
                updateemailid.value = mutuablelivedata
            }
        })
    }

    fun getdriverdetails(mContext: Context)
    {
        dashboarddata.getbasicdriverdetails(mContext)
    }
    fun getdrivermobileno(mContext: Context)
    {
        dashboarddata.getdrivermobile(mContext)
    }
    fun getemailid(mContext: Context)
    {
        dashboarddata.getemaiidd(mContext)
    }
    fun deletedb(mContext:Context)
    {
        repository.deletallrecord()
    }
    fun returndob(mContext: Context)
    {
        val calendar = Calendar.getInstance()
        val datePickerDialog = EightFoldsDatePickerDialog(
                mContext,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

                    var newmonth= (month+1)
                    var days=dayOfMonth.toString()
                    if(dayOfMonth<10)
                        days="0"+dayOfMonth

                    var months=newmonth.toString()
                    if(newmonth<10)
                        months="0"+newmonth

                    val date = "$days/$months/$year"
                    dobdate.value=date
                    val senddate = "$year-$months-$days"
                    senddobdate.value= senddate
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE)
        )

        var mindate:Int = (calendar.get(Calendar.YEAR)-18)
        datePickerDialog.setMaxDate(mindate,12,31);

        //   datePickerDialog.setTodayAsMinDate();   // sets today's date as min date
        //   datePickerDialog.setTodayAsMaxDate();    // sets today's date as max date

        datePickerDialog.show()

    }

    //get country code select
    fun defaultflagprocessor(mContext: Context,defaultflagcode:String)
    {
        val codeNameArray = defaultflagcode.split(",")
        val codes = codeNameArray[2].trim()
        val codeselected = codeNameArray[0].trim()
        countryselected.value=codes
        countryselectedwithcode.value=codeselected
    }





    fun getvalidationResult(mContext: Context,firstname:String,lastname:String,dob:String,address:String,choosecountry:String,state:String,city:String,serviceloaction:String,zipcode:String)
    {
        if(firstname.isEmpty())
        {
            countrycodestringemit.value = "1"
        }
        else if(lastname.isEmpty())
        {
            countrycodestringemit.value = "2"
        }

        else
        {

            if(dob.isEmpty())
            {
                countrycodestringemit.value = "5"
            }
            else if(address.isEmpty())
            {
                countrycodestringemit.value = "7"
            }
            else if(choosecountry.isEmpty())
            {
                countrycodestringemit.value = "8"
            }


            else if(state.isEmpty())
            {
                countrycodestringemit.value = "9"
            }
            else if(city.isEmpty())
            {
                countrycodestringemit.value = "10"
            }
            else if(zipcode.isEmpty())
            {
                countrycodestringemit.value = "11"
            }
            else
            {
                countrycodestringemit.value = "12"
            }
        }
    }
//    fun onImagecompress(mContext: Context,picturePath: File,loader:SmoothProgressBar)
//    {
//        try
//        {
//            try
//            {
//                mSessionManager = SessionManager(mContext!!)
//                val imageZipperFile = ImageZipper(mContext).setQuality(80).setMaxWidth(200).setMaxHeight(200).compressToFile(picturePath)
//                base64string = ImageZipper.getBase64forImage(imageZipperFile)
//               // mSessionManager!!.setProfileImage(base64string)
//                imageonbase64.value=base64string
//                val b = ImageZipper(mContext).compressToBitmap(picturePath)
//                saveimagetopath(b,mContext,loader)
//            }
//            catch (e: Exception)
//            {
//                e.printStackTrace()
//            }
//        }
//        catch (e: IOException)
//        {
//            e.printStackTrace()
//        }
//    }



    fun splitdocumentinfo(mContext: Context, response:String)
    {
        dashboarddata.documentinfo(mContext,response)
    }


//    fun onImagecompresscamera(mContext: Context,b:Bitmap,loader:SmoothProgressBar)
//    {
//        try
//        {
//            try
//            {
//                saveimagetopath(b,mContext,loader)
//            }
//            catch (e: Exception)
//            {
//                e.printStackTrace()
//            }
//        }
//        catch (e: IOException)
//        {
//            e.printStackTrace()
//        }
//    }





    fun splitresponse(mContext: Context,firstname:String,lastname:String,emailid:String,dob:String,gender:String,address:String,choosecountry:String,state:String,city:String,serviceloaction:String,zipcode:String,storebase64string:String)
    {
        try
        {
            mSessionManager = SessionManager(mContext)
            mSessionManager!!.createLoginSession(mSessionManager!!.getDriverId(),firstname,lastname,firstname,emailid,gender,dob,storebase64string,"2",mSessionManager!!.getUserDetails()!![mSessionManager!!.mobilecode]!!,mSessionManager!!.getUserDetails()!![mSessionManager!!.mobilenumber]!!,address,choosecountry,state,city,"",zipcode)
            mSessionManager!!.createLoginSuccess()
           // mSessionManager!!.setSteps("2")
           // successmo.value="1"
        }
        catch (e: Exception)
        {

        }
    }


    fun checkemailexist(mContext: Context,emailexist: String)
    {
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.emailcheckk))
        intent.putExtra("emailid", emailexist)
        mContext.startService(intent)
    }

    fun startprofileapi(mContext: Context,code: String,mobileno: String,email:String,first_name:String,last_name:String,image:String,gender:String,dob:String)
    {
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.profilesaveapi))
        intent.putExtra("dial_code", code)
        intent.putExtra("mobile_number", mobileno)
        intent.putExtra("email", email)
        intent.putExtra("first_name", first_name)
        intent.putExtra("last_name", last_name)
        intent.putExtra("image", image)
        intent.putExtra("gender", gender)
        intent.putExtra("dob", dob)
        mContext.startService(intent)
    }

    override fun onCleared()
    {
    }

    private fun getCountryZipCode(ssid: String): String
    {
        val loc = Locale("", ssid)
        return loc.displayCountry.trim { it <= ' ' }
    }

//    fun saveimagetopath(b:Bitmap,mContext:Context,loader:SmoothProgressBar)
//    {
//    var path:String = Environment.getExternalStorageDirectory().toString();
//    var  fOut: OutputStream
//    var file:File =  File(path, "profilepictempfile"+".jpg");
//    fOut = FileOutputStream(file);
//    b.compress(Bitmap.CompressFormat.JPEG, 85, fOut)
//    fOut.flush()
//    fOut.close()
//
//    uploadimagetoserver(mContext,file,loader)
//    }

//    fun uploadimagetoserver(mContext:Context,file:File,loader:SmoothProgressBar)
//    {
//        //Toast.makeText(mContext,mContext.getString(R.string.saving_pic),Toast.LENGTH_LONG).show()
//        loader.visibility= View.VISIBLE
//        val serviceClass = uploadimagetoserverusingamazonbucket::class.java
//        val intent = Intent(mContext, serviceClass)
//        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), file.toString())
//        intent.putExtra("state", "1")
//        mContext.startService(intent)
//    }
}

