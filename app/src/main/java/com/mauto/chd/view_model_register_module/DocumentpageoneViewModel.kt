package com.mauto.chd.view_model_register_module


import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.mauto.chd.R
import com.mauto.chd.backgroundservices.commonapifetchservice
import com.mauto.chd.SessionManagerPackage.SessionManager
import com.mauto.chd.data.steptwodocumentdb.documenttwoRepository
//import com.developers.imagezipper.ImageZipper
//import fr.castorflex.android.smoothprogressbar.SmoothProgressBar
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.util.*


class DocumentpageoneViewModel(application: Application) : AndroidViewModel(application)
{

    private var mSessionManager: SessionManager? = null
    private val locationid = MutableLiveData<String>()
    private val locationname = MutableLiveData<String>()
    private val repository: documenttwoRepository = documenttwoRepository(application)
    private val categoryid = MutableLiveData<String>()
    private val categoryname = MutableLiveData<String>()
    private lateinit var base64string: String
    private val vehicletypeid = MutableLiveData<String>()
    private val vehicletypename = MutableLiveData<String>()

    private val getmakeid = MutableLiveData<String>()
    private val getmakename = MutableLiveData<String>()

    private val getmodelid = MutableLiveData<String>()
    private val getmodelname = MutableLiveData<String>()

    private val getYear = MutableLiveData<String>()

    private val getVehicleNumber = MutableLiveData<String>()
    private val imageonbase64 = MutableLiveData<String>()
    private val responseofvehicle = MutableLiveData<String>()
    private val responseofvehiclestatus = MutableLiveData<String>()
    private val getvehcileimage = MutableLiveData<String>()


    fun locationidobserver(): MutableLiveData<String>
    {
        return locationid
    }
    fun locationnameobserver(): MutableLiveData<String>
    {
        return locationname
    }
    fun vehicleimageobserver(): MutableLiveData<String>
    {
        return getvehcileimage
    }

    fun responseofvehicleobserver(): MutableLiveData<String>
    {
        return responseofvehicle
    }
    fun rresponseofvehiclestatusobserver(): MutableLiveData<String>
    {
        return responseofvehiclestatus
    }


    fun categoryidobserver(): MutableLiveData<String>
    {
        return categoryid
    }
    fun categorynameobserver(): MutableLiveData<String>
    {
        return categoryname
    }

    fun verifyvehiclenumber(mContext: Context, params: HashMap<String, String>)
    {
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.verifyvehiclenumber))
        intent.putExtra("params", params)
        mContext.startService(intent)
    }

    fun vehicletypeidobserver(): MutableLiveData<String>
    {
        return vehicletypeid
    }
    fun vehicletypenameobserver(): MutableLiveData<String>
    {
        return vehicletypename
    }

    fun makeidobserver(): MutableLiveData<String>
    {
        return getmakeid
    }
    fun makenameobserver(): MutableLiveData<String>
    {
        return getmakename
    }


    fun modelidobserver(): MutableLiveData<String>
    {
        return getmodelid
    }
    fun modelnameobserver(): MutableLiveData<String>
    {
        return getmodelname
    }

    fun yearobserver(): MutableLiveData<String>
    {
        return getYear
    }

    fun vehiclenoobserver(): MutableLiveData<String>
    {
        return getVehicleNumber
    }


    fun deletedocumentdb()
    {
        repository.deletallrecord()
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

        getcategoryDetails(mContext)
        getVehicleTypeDetails(mContext)
        getmakeDetails(mContext)
        getmodelDetails(mContext)
        getyearDetails(mContext)
    }
    fun getlocationDetails(mContext: Context)
    {
        mSessionManager = SessionManager(mContext)
        locationid.value = mSessionManager!!.getLocationID()
        locationname.value = mSessionManager!!.getLocationName()
    }

    fun setVehicleNumber(mContext: Context, vehicleno: String)
    {
        mSessionManager = SessionManager(mContext)
        mSessionManager!!.setvehiclenumber(vehicleno)
    }





    fun choosecategory(mContext: Context, locationid: String, locationname: String)
    {
        mSessionManager = SessionManager(mContext)
        mSessionManager!!.setCategory(locationid, locationname)
        mSessionManager!!.clearVehicleType()
        mSessionManager!!.clearMake()
        mSessionManager!!.clearModel()
        mSessionManager!!.clearYear()
        getVehicleTypeDetails(mContext)
        getmakeDetails(mContext)
        getmodelDetails(mContext)
        getyearDetails(mContext)
    }

    fun getcategoryDetails(mContext: Context)
    {
        mSessionManager = SessionManager(mContext)
        categoryid.value = mSessionManager!!.getCategoryID()
        categoryname.value = mSessionManager!!.geCategoryName()
    }




    fun savevehicleimage(mContext: Context, storebase64string: String)
    {
        mSessionManager = SessionManager(mContext)
        mSessionManager!!.setVehicleImage(storebase64string)
    }


    fun hitsteponecall(mContext: Context)
    {
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.steponeapi))
        mContext.startService(intent)
    }


    fun splitresponse(mContext: Context, finalresponse: String)
    {
        mSessionManager = SessionManager(mContext)
        try
        {
            val response_json_object = JSONObject(finalresponse)
            try
            {
                val status = response_json_object.getString("status")
                val message = response_json_object.getString("response")
                if (status.equals("1"))
                {
                    val vehicle_id = response_json_object.getString("vehicle_id")
                    mSessionManager!!.setVehicleStepOneId(vehicle_id)
                }
                responseofvehicle.value = message
                responseofvehiclestatus.value = status
            }
            catch (e: Exception)
            {
            }
        }
        catch (e: Exception)
        {

        }
    }

    fun step2(mContext: Context)
    {
        mSessionManager = SessionManager(mContext)
        try
        {
            mSessionManager!!.setVehicleStepOneId("2")
            responseofvehiclestatus.value = "1"
        }
        catch (e: Exception)
        {
        }
    }


    fun getimagedata(mContext: Context)
    {
        mSessionManager = SessionManager(mContext)
        try
        {
            if(!mSessionManager!!.getVehicleImage().equals(""))
               getvehcileimage.value = mSessionManager!!.getVehicleImage()
        }
        catch (e: Exception)
        {
        }
    }


    fun getVehiclenoDetails(mContext: Context)
    {
        mSessionManager = SessionManager(mContext)
        getVehicleNumber.value = mSessionManager!!.getVehicleno()
    }

    fun choosevehicletype(mContext: Context, locationid: String, locationname: String)
    {
        mSessionManager = SessionManager(mContext)
        mSessionManager!!.setVehicleType(locationid, locationname)
        mSessionManager!!.clearMake()
        mSessionManager!!.clearModel()
        mSessionManager!!.clearYear()
        getmakeDetails(mContext)
        getmodelDetails(mContext)
        getyearDetails(mContext)
    }
    fun getVehicleTypeDetails(mContext: Context)
    {
        mSessionManager = SessionManager(mContext)
        vehicletypeid.value = mSessionManager!!.getVehicleTypeID()
        vehicletypename.value = mSessionManager!!.getVehicleTypeName()
    }



    fun choosemaketype(mContext: Context, locationid: String, locationname: String)
    {
        mSessionManager = SessionManager(mContext)
        mSessionManager!!.setMake(locationid, locationname)
        mSessionManager!!.clearModel()
        mSessionManager!!.clearYear()
        getmodelDetails(mContext)
        getyearDetails(mContext)
    }
    fun getmakeDetails(mContext: Context)
    {
        mSessionManager = SessionManager(mContext)
        var makeiname:String= mSessionManager!!.getMakeName()
        getmakeid.value = mSessionManager!!.getMakeID()
        getmakename.value = makeiname
    }



    fun choosemodeltype(mContext: Context, locationid: String, locationname: String)
    {
        mSessionManager = SessionManager(mContext)
        mSessionManager!!.setModel(locationid, locationname)
        mSessionManager!!.clearYear()
        getyearDetails(mContext)
    }
    fun getmodelDetails(mContext: Context)
    {
        mSessionManager = SessionManager(mContext)
        getmodelid.value = mSessionManager!!.getModelID()
        getmodelname.value = mSessionManager!!.getModelName()
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
//                // mSessionManager!!.setProfileImage(base64string)
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
        inImage.compress(Bitmap.CompressFormat.JPEG, 70, bytes)
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

    fun chooseyear(mContext: Context, year: String)
    {
        mSessionManager = SessionManager(mContext)
        mSessionManager!!.setYear(year)
    }
    fun getyearDetails(mContext: Context)
    {
        mSessionManager = SessionManager(mContext)
        getYear.value = mSessionManager!!.getYearID()
    }


//    fun saveimagetopath(b: Bitmap, mContext: Context, loader: SmoothProgressBar, selectedImageUri: Uri)
//    {
//        val filePath = arrayOf(MediaStore.Images.Media.DATA)
//        val c: Cursor? = mContext.getContentResolver().query(selectedImageUri, filePath, null, null, null)
//        c!!.moveToFirst()
//        val columnIndex: Int = c.getColumnIndex(filePath[0])
//        val picturePath: String = c.getString(columnIndex)
//        c.close()
//        val curFile = File(picturePath)
//
//
////        var path:String = Environment.getExternalStorageDirectory().toString();
////        var  fOut: OutputStream
////        var file:File =  File(path, "vehicleimagecab"+".jpg");
////        fOut = FileOutputStream(file);
////        b.compress(Bitmap.CompressFormat.JPEG, 85, fOut)
////        fOut.flush()
////        fOut.close()
//
//        uploadimagetoserver(mContext, curFile, loader)
//    }

//    fun uploadimagetoserver(mContext: Context, file: File, loader: SmoothProgressBar)
//    {
//        loader.visibility= View.VISIBLE
//        val serviceClass = uploadimagetoserverusingamazonbucket::class.java
//        val intent = Intent(mContext, serviceClass)
//        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), file.toString())
//        intent.putExtra("state", "2")
//        mContext.startService(intent)
//    }
    override fun onCleared()
    {
    }
}

