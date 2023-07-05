//package com.cabilyhandyforalldinedoo.chd.ui.registeration
//
//import android.Manifest
//import android.annotation.SuppressLint
//import android.app.Activity
//import android.content.Context
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.graphics.Bitmap
//import android.graphics.Color
//import android.net.Uri
//import android.os.Build
//import android.os.Bundle
//import android.provider.MediaStore
//import android.provider.Settings
//import android.text.Editable
//import android.text.TextWatcher
//import android.util.Log
//import android.view.Gravity
//import android.view.View
//import android.widget.FrameLayout
//import android.widget.LinearLayout
//import android.widget.TextView
//import androidx.annotation.RequiresApi
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProviders
//import cabily.handyforall.dinedoo.R
//import com.cabilyhandyforalldinedoo.chd.EventBusConnection.IntentServiceResult
//import com.cabilyhandyforalldinedoo.chd.SessionManagerPackage.SessionManager
//import com.cabilyhandyforalldinedoo.chd.ViewModelRegisterModule.documentpageoneViewModel
//import com.cabilyhandyforalldinedoo.chd.commonutils.AppUtils
//import com.cabilyhandyforalldinedoo.chd.commonutils.Constants
//import com.google.android.material.bottomsheet.BottomSheetDialog
//import com.google.android.material.snackbar.Snackbar
//import kotlinx.android.synthetic.main.documentpageone.*
//import kotlinx.android.synthetic.main.documentpageone.close
////import kotlinx.android.synthetic.main.documentpageone.loader
//import kotlinx.android.synthetic.main.documentpageone.nextlayout
////import kotlinx.android.synthetic.main.documentpagetwo.*
//import kotlinx.android.synthetic.main.registerpagetwo.*
//import org.greenrobot.eventbus.EventBus
//import org.greenrobot.eventbus.Subscribe
//import org.greenrobot.eventbus.ThreadMode
//import org.json.JSONException
//import org.json.JSONObject
//import java.io.ByteArrayOutputStream
//import java.io.File
//import java.util.HashMap
//
//
//class documentpageone : LocaleAwareCompatActivity()
//{
//
//    lateinit var getvalueromprevious:String
//    var locationid:String = ""
//    lateinit var categoryid:String
//    var vehicletypeid:String=""
//    var makeid:String=""
//    var modelid:String=""
//    var getfromadd:Int = 0
//    var yearid:String=""
//    lateinit var mSessionManager: SessionManager
//    private lateinit var mContext: Activity
//    lateinit var mViewModel: documentpageoneViewModel
//    private val LOCATIONCODEEREQUEST = 113
//    private val CATEGORYCODEEREQUEST = 114
//    private val VEHICLECODEEREQUEST = 115
//    private val VEHICLEMAKECODEEREQUEST = 116
//    private val VEHICLEMODELCODEEREQUEST = 117
//    private val VEHICLEYEARCODEEREQUEST = 118
//    private val PICK_CAMERA_IMAGE = 1
//    private val PICK_GALLERY_IMAGE = 2
//    private val gallerypermisssioncode = 999
//    private val camerapermisssioncode = 888
//    var  storebase64string:String=""
//
//    override fun onCreate(savedInstanceState: Bundle?)
//    {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.documentpageone)
//
//        mContext = this@documentpageone
//        getextravalue()
//        initViewModel()
//        editextlistenercall()
//
//        choosefile.setOnClickListener {
//            camerasheeet()
//        }
//
//
//        locationtype.setOnClickListener {
//            vehicledocumentselect(LOCATIONCODEEREQUEST,"1")
//        }
//
//        nofile.setOnClickListener {
//           if(!storebase64string.equals(""))
//           {
//               val intent_otppage = Intent(mContext, imagepreviewpage::class.java)
//               intent_otppage.putExtra("imagepath",getString(R.string.amazonbucketurlvehicle)+storebase64string)
//               Log.d("dfhjdhfh",getString(R.string.amazonbucketurlvehicle)+storebase64string)
//               startActivity(intent_otppage)
//           }
//        }
//
//        backtologin.setOnClickListener {
//            mSessionManager = SessionManager(applicationContext!!)
//            mSessionManager.clearalldata()
//            backtologin(applicationContext)
//            mViewModel.deletedocumentdb()
//        }
//        backtologinim.setOnClickListener {
//            mSessionManager = SessionManager(applicationContext!!)
//            mSessionManager.clearalldata()
//            backtologin(applicationContext)
//            mViewModel.deletedocumentdb()
//        }
//
////        category.setOnClickListener {
////            if(!locationtype.text.equals(""))
////            vehicledocumentselect(CATEGORYCODEEREQUEST,"2")
////            else
////                commonerrorpage()
////        }
////        vehicletype.setOnClickListener {
////            if(!category.text.equals(""))
////            vehicledocumentselect(VEHICLECODEEREQUEST,"3")
////            else
////                commonerrorpage()
////        }
//
//        make.setOnClickListener {
//            if(!entervehicleno.text.equals(""))
//
//                 vehicledocumentselect(VEHICLEMAKECODEEREQUEST,"4")
//            else
//                commonerrorpage()
//        }
//
//        model.setOnClickListener {
//            if(!make.text.equals(""))
//               vehicledocumentselect(VEHICLEMODELCODEEREQUEST,"5")
//            else
//                commonerrorpage()
//        }
//        year.setOnClickListener {
//            if(!model.text.equals(""))
//            vehicledocumentselect(VEHICLEYEARCODEEREQUEST,"6")
//            else
//                commonerrorpage()
//        }
//        close.setOnClickListener {
//            finish()
//        }
//        numbersuccess.setOnClickListener {
//            entervehicleno.getText().clear()
//            AppUtils.hideKeyboard(mContext, entervehicleno!!)
//        }
//        nextlayout.setOnClickListener {
//            if(yearid.equals("") || entervehicleno.text.toString().equals("") || storebase64string.equals(""))
//            {
//                AppUtils.commonerrorsheet(mContext,getString(R.string.failed),getString(R.string.plaesefill))
//            }
//            else
//            {
////                loader.visibility = View.VISIBLE
//
//
//            }
//        }
//        next.setOnClickListener {
//            if(yearid.equals("") || entervehicleno.text.toString().equals("") || storebase64string.equals(""))
//            {
//                AppUtils.commonerrorsheet(mContext,getString(R.string.failed),getString(R.string.plaesefill))
//            }
//            else
//            {
//
//                var mSessionManager: SessionManager? = null
//                mSessionManager = SessionManager(mContext)
//
//                var params = HashMap<String, String>()
//                params.clear()
////                loader.visibility = View.VISIBLE
//                val vehicleno = mSessionManager!!.getVehicleno()
//                val vehicle_model_year = mSessionManager!!.getYearID()
//                val vehicleimage = mSessionManager!!.getVehicleImage()
//                val type = JSONObject()
//                try {
//                    type.put("id", mSessionManager!!.getVehicleTypeID())
//                    type.put("name", mSessionManager!!.getVehicleTypeName())
//                } catch (e: JSONException) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace()
//                }
//
//
//                val location = JSONObject()
//                try {
//                    location.put("id", mSessionManager!!.getLocationID())
//                    location.put("name", mSessionManager!!.getLocationName())
//                } catch (e: JSONException) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace()
//                }
//
//
//                val category = JSONObject()
//                try {
//                    category.put("id", mSessionManager!!.getCategoryID())
//                    category.put("name", mSessionManager!!.geCategoryName())
//                } catch (e: JSONException) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace()
//                }
//
//                val make = JSONObject()
//                try {
//                    make.put("id", mSessionManager!!.getMakeID())
//                    make.put("name", mSessionManager!!.getMakeName())
//                } catch (e: JSONException) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace()
//                }
//
//
//                val model = JSONObject()
//                try {
//                    model.put("id", mSessionManager!!.getModelID())
//                    model.put("name", mSessionManager!!.getModelName())
//                } catch (e: JSONException) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace()
//                }
//
//
//
//                val vehicle_info = JSONObject()
//                try {
//                    vehicle_info.put("vehicle_number", vehicleno)
//                    vehicle_info.put("image", vehicleimage)
//                    vehicle_info.put("location", location)
//                    vehicle_info.put("category", category)
//                    vehicle_info.put("type", type)
//                    vehicle_info.put("make", make)
//                    vehicle_info.put("model", model)
//                    vehicle_info.put("year", vehicle_model_year)
//                    vehicle_info.put("color", "red")
//                    vehicle_info.put("doors", "5")
//                    vehicle_info.put("seats", "6")
//                } catch (e: JSONException) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace()
//                }
//
//
//
//
//                var op:String= vehicle_info.toString()
//
//
//                params.put("driver_id", mSessionManager!!.getDriverId())
//                params.put("vehicle_info", op)
//                params.put("vehicle_id", mSessionManager!!.gettempvehicleid())
//
//                mViewModel.verifyvehiclenumber(mContext,params)
//                Log.d("cjjswjsw", params.toString())
//
//
//
//            }
//        }
//    }
//
//
//    fun getextravalue()
//    {
//        if (intent?.hasExtra("getfromadd")!!)
//        {
//            backtologin.visibility = View.GONE
//            backtologinim.visibility = View.GONE
//            getfromadd = 1
//        }
//    }
//
//    fun commonerrorpage()
//    {
//        AppUtils.commonerrorsheet(mContext,getString(R.string.failed),getString(R.string.below_foeld))
//    }
//
//    fun editextlistenercall()
//    {
//        entervehicleno.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(p0: Editable?) {
//            }
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//
//
//                mViewModel.setVehicleNumber(mContext,p0!!.toString())
//                Log.d("fsvgdgfghfs", mViewModel.setVehicleNumber(mContext,p0!!.toString()).toString())
//                if (p0!!.length == 0)
//                {
//                    numbersuccess.visibility = View.INVISIBLE
//                }
//                else
//                {
//                    numbersuccess.visibility = View.VISIBLE
//                }
//
//            }
//        })
//    }
//
//    private fun initViewModel()
//    {
//        mViewModel = ViewModelProviders.of(this).get(documentpageoneViewModel::class.java)
//        mViewModel.getlocationDetails(mContext)
//        mViewModel.getcategoryDetails(mContext)
//        mViewModel.getVehicleTypeDetails(mContext)
//        mViewModel.getmakeDetails(mContext)
//        mViewModel.getmodelDetails(mContext)
//        mViewModel.getyearDetails(mContext)
//        mViewModel.getVehiclenoDetails(mContext)
//
//
//        mViewModel.vehiclenoobserver().observe(this, Observer {
//            entervehicleno.setText(it)
//            entervehicleno.setSelection(entervehicleno.getText().length)
//        })
//
//
//        mViewModel.responseofvehicleobserver().observe(this, Observer {
//            // Toast.makeText(applicationContext,it,Toast.LENGTH_LONG).show()
//        })
//
//
//
//
//        mViewModel.locationidobserver().observe(this, Observer {
//            locationid=it
//        })
//
//        mViewModel.locationnameobserver().observe(this, Observer {
//            if(!it.equals(""))
//            {
//                runOnUiThread {
//                    locationtype.text = it
//                    locationsuccess.visibility= View.VISIBLE
//                }
//            }
//            else
//            {
//                runOnUiThread {
//                    locationtype.text = ""
//                    locationtype.hint = getString(R.string.locationnn)
//                    locationsuccess.visibility= View.INVISIBLE
//                }
//            }
//        })
//
//        mViewModel.categoryidobserver().observe(this, Observer {
//            categoryid=it
//        })
//
//        mViewModel.categorynameobserver().observe(this, Observer {
//            if(!it.equals(""))
//            {
//                runOnUiThread {
//                    category.text = it
//                    categorysuccess.visibility= View.VISIBLE
//                }
//            }
//            else
//            {
//                runOnUiThread {
//                    category.text = ""
//                    category.hint = getString(R.string.categoryy)
//                    categorysuccess.visibility= View.INVISIBLE
//                }
//            }
//        })
//
//
//        mViewModel.vehicletypeidobserver().observe(this, Observer {
//            vehicletypeid=it
//        })
//
//        mViewModel.vehicleimageobserver().observe(this, Observer {
//            storebase64string=it
//            Log.d("dfhjdhfh5514",storebase64string)
//
//            nofile.setText(getString(R.string.previewme))
//            nofile.setTextColor(Color.parseColor("#427fed"))
//        })
//
//        mViewModel.vehicletypenameobserver().observe(this, Observer {
//            if(!it.equals(""))
//            {
//                runOnUiThread {
//                    vehicletype.text = it
//                    vtypesuccess.visibility= View.VISIBLE
//                }
//            }
//            else
//            {
//                runOnUiThread {
//                    vehicletype.text = ""
//                    vehicletype.hint = getString(R.string.vehicletypee)
//                    vtypesuccess.visibility= View.INVISIBLE
//                }
//            }
//        })
//
//
//
//        mViewModel.makeidobserver().observe(this, Observer {
//            makeid=it
//        })
//
//        mViewModel.makenameobserver().observe(this, Observer {
//            if(!it.equals(""))
//            {
//                runOnUiThread {
//                    make.text = it
//                    makesuccess.visibility= View.VISIBLE
//                }
//            }
//            else
//            {
//                runOnUiThread {
//                    make.text = ""
//                    make.hint = getString(R.string.vehiclemake)
//                    makesuccess.visibility= View.INVISIBLE
//                }
//            }
//        })
//
//
//        mViewModel.modelidobserver().observe(this, Observer {
//            modelid=it
//        })
//
//        mViewModel.modelnameobserver().observe(this, Observer {
//            if(!it.equals(""))
//            {
//                runOnUiThread {
//                    model.text = it
//                    modelsuccess.visibility= View.VISIBLE
//                }
//            }
//            else
//            {
//                runOnUiThread {
//                    model.text = ""
//                    model.hint = getString(R.string.vehiclemodel)
//                    modelsuccess.visibility= View.INVISIBLE
//                }
//            }
//        })
//
//        mViewModel.yearobserver().observe(this, Observer {
//            yearid=it
//            if(!it.equals(""))
//            {
//                runOnUiThread {
//                    year.text = it
//                    yearsuccess.visibility= View.VISIBLE
//                }
//            }
//            else
//            {
//                runOnUiThread {
//                    year.text = ""
//                    year.hint = getString(R.string.vehicleyear)
//                    yearsuccess.visibility= View.INVISIBLE
//                }
//            }
//        })
//    }
//
//    fun vehicledocumentselect(requestCode: Int,putextrastring:String)
//    {
////        if(loader.visibility == View.VISIBLE)
////        {
////            AppUtils.commonerrorsheet(mContext,getString(R.string.failed),getString(R.string.waittillimageupload))
////        }
////        else
////        {
////            val intent2 = Intent(mContext, VehicleDetailsSelection::class.java)
////            intent2.putExtra("vehicled",putextrastring)
////            intent2.putExtra("locationid",locationid)
////            intent2.putExtra("categoryid",categoryid)
////            intent2.putExtra("vehicletypeid",vehicletypeid)
////            intent2.putExtra("makeid",makeid)
////            intent2.putExtra("modelid",modelid)
////            intent2.putExtra("skipingid","")
////            startActivityForResult(intent2, requestCode)
////        }
//    }
//
//
//    @SuppressLint("MissingSuperCall")
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
//    {
//        if(data !=null)
//        {
//            if (requestCode == LOCATIONCODEEREQUEST)
//            {
//                getvalueromprevious = data?.getStringExtra(Constants.INTENT_OBJECT).toString()
//                val splitvalues = getvalueromprevious.split(",")
//                locationid = splitvalues[0].trim()
//                val value = splitvalues[1].trim()
//                locationtype.text = value
//                locationsuccess.visibility= View.VISIBLE
//                mViewModel.chooselocation(mContext,locationid,value)
//            }
//            else if (requestCode == CATEGORYCODEEREQUEST)
//            {
//                getvalueromprevious = data?.getStringExtra(Constants.INTENT_OBJECT).toString()
//                val splitvalues = getvalueromprevious.split(",")
//                categoryid = splitvalues[0].trim()
//                val value = splitvalues[1].trim()
//                category.text = value
//                categorysuccess.visibility= View.VISIBLE
//                mViewModel.choosecategory(mContext,categoryid,value)
//            }
//            else if (requestCode == VEHICLECODEEREQUEST)
//            {
//                getvalueromprevious = data?.getStringExtra(Constants.INTENT_OBJECT).toString()
//                val splitvalues = getvalueromprevious.split(",")
//                vehicletypeid = splitvalues[0].trim()
//                val value = splitvalues[1].trim()
//                vehicletype.text = value
//                vtypesuccess.visibility= View.VISIBLE
//                mViewModel.choosevehicletype(mContext,vehicletypeid,value)
//            }
//            else if (requestCode == VEHICLEMAKECODEEREQUEST)
//            {
//                getvalueromprevious = data?.getStringExtra(Constants.INTENT_OBJECT).toString()
//                val splitvalues = getvalueromprevious.split(",")
//                makeid = splitvalues[0].trim()
//                val value = splitvalues[1].trim()
//                make.text = value
//                makesuccess.visibility= View.VISIBLE
//                mViewModel.choosemaketype(mContext,makeid,value)
//            }
//            else if (requestCode == VEHICLEMODELCODEEREQUEST)
//            {
//                getvalueromprevious = data?.getStringExtra(Constants.INTENT_OBJECT).toString()
//                val splitvalues = getvalueromprevious.split(",")
//                modelid = splitvalues[0].trim()
//                val value = splitvalues[1].trim()
//                model.text = value
//                modelsuccess.visibility= View.VISIBLE
//                mViewModel.choosemodeltype(mContext,modelid,value)
//            }
//            else if (requestCode == VEHICLEYEARCODEEREQUEST)
//            {
//                getvalueromprevious = data?.getStringExtra(Constants.INTENT_OBJECT).toString()
//                val splitvalues = getvalueromprevious.split(",")
//                yearid = splitvalues[0].trim()
//                val value = splitvalues[1].trim()
//                year.text = value
//                yearsuccess.visibility= View.VISIBLE
//                mViewModel.chooseyear(mContext,value)
//            }
//            if (requestCode == PICK_GALLERY_IMAGE)
//            {
//                if(data != null)
//                {
//                    var picturePath = ""
//                    var selectedImageUri: Uri = data!!.getData()
//                    picturePath = getPath(applicationContext, selectedImageUri)
//                    var actualImage: File = File(picturePath)
////                    mViewModel.onImagecompress(mContext,actualImage,loader,selectedImageUri)
//                }
//            }
//            else if (requestCode == PICK_CAMERA_IMAGE)
//            {
//                if(data != null)
//                {
//                    val extras = data?.getExtras()
//                    val imageBitmap = extras?.get("data") as Bitmap
//                    var myUri =  mViewModel.getImageUri(mContext,imageBitmap)
//
//                    var baos: ByteArrayOutputStream =  ByteArrayOutputStream()
//                    imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
//                    val b = baos.toByteArray()
////                    mViewModel.onImagecompresscamera(mContext,imageBitmap,loader, myUri!!)
//                }
//            }
//        }
//    }
//
//
//
//
//    private fun getstoragepermission()
//    {
//        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
//                ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(mContext, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE),
//                    gallerypermisssioncode)
//        }
//        else
//        {
//            callgallerypart()
//        }
//    }
//    private fun getcamerapermission()
//    {
//        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
//                ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
//        {
//            ActivityCompat.requestPermissions(mContext, arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE),camerapermisssioncode)
//        }
//        else
//        {
//            cameraimage()
//        }
//    }
//    @RequiresApi(Build.VERSION_CODES.M)
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
//    {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        when (requestCode) {
//            gallerypermisssioncode -> {
//                if ((grantResults[0] == PackageManager.PERMISSION_GRANTED) && (grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
//                    callgallerypart()
//                } else {
//                }
//            }
//            camerapermisssioncode -> {
//                if ((grantResults[0] == PackageManager.PERMISSION_GRANTED))
//                {
//                    cameraimage()
//                }
//            }
//
//
//        }
//        var isPermissionDenied = false
//        var isPermisionRequest = false
//        for (i in 0 until grantResults.size) {
//            if (grantResults[i] === PackageManager.PERMISSION_DENIED) {
//                val showRationale = shouldShowRequestPermissionRationale(permissions[i])
//                if (!showRationale) {
//                    isPermissionDenied = true
//                } else {
//                    isPermisionRequest = true
//                }
//            }
//        }
//        if (isPermissionDenied){
//            val intent = Intent()
//            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
//            val uri: Uri = Uri.fromParts("package", packageName, null)
//            intent.data = uri
//            startActivity(intent)
//        }
//    }
//
//    fun cameraimage()
//    {
//        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        startActivityForResult(intent, PICK_CAMERA_IMAGE)
//    }
//    fun callgallerypart()
//    {
//        val intent=Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        startActivityForResult(intent, PICK_GALLERY_IMAGE)
//    }
//    fun getPath(context: Context, uri: Uri): String
//    {
//        var result: String? = null
//        val proj = arrayOf(MediaStore.Images.Media.DATA)
//        val cursor = context.contentResolver.query(uri, proj, null, null, null)
//        if (cursor != null) {
//            if (cursor.moveToFirst()) {
//                val column_index = cursor.getColumnIndexOrThrow(proj[0])
//                result = cursor.getString(column_index)
//            }
//            cursor.close()
//        }
//        if (result == null) {
//            result = "Not found"
//        }
//        return result
//    }
//
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun doThis(intentServiceResult: IntentServiceResult)
//    {
//        var finalresponse: String = intentServiceResult.resultValue
////        loader.visibility = View.INVISIBLE
//        var apiName: String = intentServiceResult.apiName
//        if (apiName.equals(getString(R.string.steponeapi)))
//        {
//            if (finalresponse != "failed")
//            {
//                mViewModel.splitresponse(mContext,finalresponse)
//            }
//        }
//        else  if (apiName.equals(getString(R.string.verifyvehiclenumber)))
//        {
//            if (finalresponse == "1")
//            {
//                documentpagetwointent()
//            }
//            else if (finalresponse == "0")
//            {
//                removebottomsheet(getString(R.string.vehiclenumber_alreadyregistered))
//            }
//        }
//        else if (apiName.equals(getString(R.string.amazonimageuploaded)))
//        {
//            if (finalresponse != "failed")
//            {
//                storebase64string=finalresponse
//                nofile.setText(getString(R.string.previewme))
//                nofile.setTextColor(Color.parseColor("#427fed"))
//                mViewModel.savevehicleimage(mContext,storebase64string)
//            }
//            else
//            {
//                storebase64string=""
//                nofile.setText(getString(R.string.nofielchoosen))
//                nofile.setTextColor(Color.parseColor("#000000"))
//                mViewModel.savevehicleimage(mContext,"")
//                AppUtils.commonerrorsheet(mContext,getString(R.string.failed),getString(R.string.vehfailed))
//            }
//        }
//    }
//    override fun onPause()
//    {
//        super.onPause()
//    }
//    override fun onResume()
//    {
//        super.onResume()
//        if(!EventBus.getDefault().isRegistered(this))
//        {
//            EventBus.getDefault().register(this)
//        }
//        mViewModel.getimagedata(mContext)
//    }
//    override fun onDestroy()
//    {
//        super.onDestroy()
//        if(EventBus.getDefault().isRegistered(this))
//        {
//            EventBus.getDefault().unregister(this);
//        }
//    }
//
//    fun documentpagetwointent()
//    {
////        val intent2 = Intent(mContext, documentpagetwo::class.java)
////        if(getfromadd == 1)
////        {
////            intent2.putExtra("getfromadd", "1")
////        }
////        startActivity(intent2)
//    }
//
//    fun backtologin(mContext: Context)
//    {
//        val intent2 = Intent(mContext, splashpage::class.java)
//        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//        mContext.startActivity(intent2)
//    }
//
//
//    fun removebottomsheet(name:String)
//    {
//        val bottomSheetDialog =  BottomSheetDialog(this)
//        val view = getLayoutInflater().inflate(R.layout.errorlisten, null);
//        bottomSheetDialog.setContentView(view)
//        val subcontent = view.findViewById(R.id.subcontent) as TextView
//        subcontent.setText(name)
//        bottomSheetDialog.show()
//    }
//
//    fun camerasheeet()
//    {
//        val bottomSheetDialog =  BottomSheetDialog(this)
//        val view = getLayoutInflater().inflate(R.layout.includechooseselection, null);
//        bottomSheetDialog.setContentView(view)
//        val fromcamera = view.findViewById(R.id.fromcamera) as LinearLayout
//        val fromgallery = view.findViewById(R.id.fromgallery) as LinearLayout
//
//        val title = view.findViewById(R.id.title) as TextView
//        val subcontent = view.findViewById(R.id.subcontent) as TextView
//
//        title.setText(getString(R.string.veh_image))
//        subcontent.setText(getString(R.string.uploadyourphotoo))
//
//        fromcamera.setOnClickListener {
//            bottomSheetDialog.dismiss()
//            getcamerapermission()
//        }
//
//        fromgallery.setOnClickListener {
//            bottomSheetDialog.dismiss()
//            getstoragepermission()
//        }
//
//        bottomSheetDialog.show()
//    }
//    //common error notification page
//    fun commsnackbaralert(message:String)
//    {
//        val snack = Snackbar.make(toplayout,message, Snackbar.LENGTH_LONG)
//        var view:View = snack.getView()
//        var params: FrameLayout.LayoutParams = FrameLayout.LayoutParams(view.getLayoutParams())
//        params.gravity = Gravity.TOP;
//        view.setLayoutParams(params)
//        snack.show()
//    }
//
//}
