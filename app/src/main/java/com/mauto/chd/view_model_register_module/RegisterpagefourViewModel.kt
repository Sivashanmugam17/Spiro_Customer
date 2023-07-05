package com.mauto.chd.view_model_register_module


import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mauto.chd.R
import com.mauto.chd.backgroundservices.commonapifetchservice
import com.mauto.chd.SessionManagerPackage.SessionManager
import com.mauto.chd.ui.registeration.EightFoldsDatePickerDialog
//import com.developers.imagezipper.ImageZipper
//import fr.castorflex.android.smoothprogressbar.SmoothProgressBar
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


class RegisterpagefourViewModel : ViewModel()
{
    private val movenext = MutableLiveData<String>()
    private val countrycodestringemit = MutableLiveData<String>()
    private val countryselected = MutableLiveData<String>()
    private val countryselectedwithcode = MutableLiveData<String>()
    private val imageonbase64 = MutableLiveData<String>()
    private val successmo = MutableLiveData<String>()
    private val dobdate = MutableLiveData<String>()
    private val senddobdate = MutableLiveData<String>()
    private var mSessionManager: SessionManager? = null
    private lateinit var base64string: String
    var cal = Calendar.getInstance()



    fun countrycodeobservervalue(): MutableLiveData<String>
    {
        return countrycodestringemit
    }

    fun countryselectedobservervalue(): MutableLiveData<String>
    {
        return countryselected
    }
    fun countryselectedwithcodeobservervalue(): MutableLiveData<String>
    {
        return countryselectedwithcode
    }
    fun movenextobserver(): MutableLiveData<String>
    {
        return movenext
    }
    fun chooselocation(mContext: Context, locationid: String, locationname: String)
    {
        mSessionManager = SessionManager(mContext)
        mSessionManager!!.setLocation(locationid, locationname)
        mSessionManager!!.clearCategory()
        mSessionManager!!.clearVehicleType()
        mSessionManager!!.clearMake()
        mSessionManager!!.clearModel()
        mSessionManager!!.clearYear()


    }



    fun success(): MutableLiveData<String>
    {
        return successmo
    }


    fun imagebase64observervalue(): MutableLiveData<String>
    {
        return imageonbase64
    }
    fun dobdateobserver(): MutableLiveData<String>
    {
        return dobdate
    }
    fun dobdateobserver1(): MutableLiveData<String>
    {
        return senddobdate
    }
    fun returndob(mContext: Context)
    {
        val calendar = Calendar.getInstance()
        val datePickerDialog = EightFoldsDatePickerDialog(
                mContext,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

                    var newmonth = (month + 1)
                    var days = dayOfMonth.toString()
                    if (dayOfMonth < 10)
                        days = "0" + dayOfMonth

                    var months = newmonth.toString()
                    if (newmonth < 10)
                        months = "0" + newmonth

                    val date = "$days/$months/$year"
                    dobdate.value = date
                    val senddate = "$year-$months-$days"
                    senddobdate.value = senddate
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE)
        )

        var mindate:Int = (calendar.get(Calendar.YEAR)-18)
        datePickerDialog.setMaxDate(mindate, 12, 31);

        //   datePickerDialog.setTodayAsMinDate();   // sets today's date as min date
        //   datePickerDialog.setTodayAsMaxDate();    // sets today's date as max date

        datePickerDialog.show()

    }

    //get country code select
    fun defaultflagprocessor(mContext: Context, defaultflagcode: String)
    {
        val codeNameArray = defaultflagcode.split(",")
        val codes = codeNameArray[2].trim()
        val codeselected = codeNameArray[0].trim()
        countryselected.value=codes
        countryselectedwithcode.value=codeselected
    }





    fun getvalidationResult(mContext: Context, firstname: String, lastname: String, emailid: String, dob: String, address: String, choosecountry: String, state: String, city: String, serviceloaction: String, zipcode: String)
    {
        if(firstname.isEmpty())
        {
            countrycodestringemit.value = "1"
        }
        else if(lastname.isEmpty())
        {
            countrycodestringemit.value = "2"
        }
        else if(emailid.isEmpty())
        {
            countrycodestringemit.value = "3"
        }
        else
        {
            if(!emailvalidornot(emailid))
            {
                countrycodestringemit.value = "4"
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
    }


//    fun onImagecompress(mContext: Context, picturePath: File, loader: SmoothProgressBar, selectedImageUri: Uri)
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
//                saveimagetopath(b, mContext, loader, selectedImageUri)
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


    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        Log.d("dfsggfdg5774cdf",path)
        return Uri.parse(path)
    }

//    fun onImagecompresscamera(mContext: Context, b: Bitmap, loader: SmoothProgressBar,selectedImageUri: Uri)
//    {
//        try
//        {
//            try
//            {
//                saveimagetopath(b, mContext, loader,selectedImageUri)
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

    fun emailvalidornot(email: String): Boolean
    {
        var expression:String = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        var pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        var matcher: Matcher = pattern.matcher(email)
        return matcher.matches()
    }

    fun savedriverprofiledata(mContext: Context, response: String)
    {
        val response_json_object = JSONObject(response)
        try
        {

            val status = response_json_object.getString("status")
            Log.d("dfhfgjfh", response_json_object.toString())
            if (status.equals("1"))
            {

                val response = response_json_object.getString("response")
                val response_json_object = JSONObject(response)
                val driver_profile= response_json_object.getJSONObject("driver_profile")
                val driver_id = driver_profile.getString("driver_id")
                val driver_name = driver_profile.getString("first_name")
                val email = driver_profile.getString("email")
//                val driver_image = driver_profile.getString("driver_image")
                val driver_image="d6476d7bea3e1d1a277be6129f250d20.jpg"

                val dial_code = driver_profile.getString("dial_code")
//                val mobile_number = response_json_object.getString("mobile_number")
                val mobile_number = driver_profile.getString("phone_number")
                var mstage =driver_profile.getString("stage")
                mSessionManager?.setdocumentstage2(mstage)

                var currentstage =""
                if (mstage.equals("UPLOAD_DRIVER_DOC")){
                    currentstage="driver"
                    mSessionManager?.setdocumentstage(currentstage)

                } else if (mstage.equals("ATTACH_VEHICLE")){
                    currentstage=""
                    mSessionManager?.setdocumentstage(currentstage)

                }


                var mSessionManager: SessionManager? = null
                mSessionManager = SessionManager(mContext)
                mSessionManager!!.setHasseesion(true)
                mSessionManager!!.driverProfileData(driver_id, driver_name, driver_image, email, dial_code, mobile_number)
                mSessionManager!!.setvehiclenumber("")
                mSessionManager!!.setModelnamealone("")
                mSessionManager!!.setReview("0")
                mSessionManager!!.setDocPending("0")

                movenext.value = "1"
            }
        }
        catch (e: Exception)
        {
        }
    }


    fun splitresponse(mContext: Context, firstname: String, lastname: String, emailid: String, dob: String, gender: String, address: String, choosecountry: String, state: String, city: String, serviceloaction: String, zipcode: String, mobileno: String, codeno: String, storebase64string: String)
    {
        try
        {
            mSessionManager = SessionManager(mContext)
            mSessionManager!!.createLoginSession("", firstname, lastname, firstname, emailid, gender, dob, storebase64string, "2", codeno, mobileno, address, choosecountry, state, city, "", zipcode)
            mSessionManager!!.createLoginSuccess()
           // mSessionManager!!.setSteps("2")
           // successmo.value="1"
        }
        catch (e: Exception)
        {

        }
    }


    fun checkemailexist(mContext: Context, emailexist: String)
    {
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.emailcheckk))
        intent.putExtra("emailid", emailexist)
        mContext.startService(intent)
    }

    fun startprofileapi(mContext: Context, code: String, mobileno: String, email: String, first_name: String, last_name: String, image: String, gender: String, dob: String)
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

//    fun saveimagetopath(b: Bitmap, mContext: Context, loader: SmoothProgressBar, selectedImageUri: Uri)
//    {
//
//
//        val filePath = arrayOf(MediaStore.Images.Media.DATA)
//        val c: Cursor? = mContext.getContentResolver().query(selectedImageUri, filePath, null, null, null)
//        c!!.moveToFirst()
//        val columnIndex: Int = c.getColumnIndex(filePath[0])
//        val picturePath: String = c.getString(columnIndex)
//        c.close()
//        val curFile = File(picturePath)
//
//
////    var path:String = Environment.getExternalStorageDirectory().toString();
////    var  fOut: OutputStream
////    var file:File =  File(path, "profilepictempfile" + ".jpg");
////        Log.d("ddgfsfgsg", file.toString())
////        Log.d("d345sfgdfdsg", path.toString())
////
////    fOut = FileOutputStream(file);
////    Log.d("mfout", fOut.toString())
////
////    b.compress(Bitmap.CompressFormat.JPEG, 85, fOut)
////    fOut.flush()
////    fOut.close()
//
//    uploadimagetoserver(mContext, curFile, loader)
//    }

//    fun uploadimagetoserver(mContext: Context, file: File, loader: SmoothProgressBar)
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

