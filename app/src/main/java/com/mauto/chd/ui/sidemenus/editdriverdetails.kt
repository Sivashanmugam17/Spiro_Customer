//package com.cabilyhandyforalldinedoo.chd.ui.sidemenus
//
//import android.Manifest
//import android.annotation.SuppressLint
//import android.app.Activity
//import android.content.Context
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.graphics.Bitmap
//import android.graphics.BitmapFactory
//import android.graphics.drawable.Drawable
//import android.net.Uri
//import android.os.Bundle
//import android.os.StrictMode
//import android.provider.MediaStore
//import android.text.SpannableStringBuilder
//import android.util.Base64
//import android.view.View
//import android.view.WindowManager
//import android.widget.LinearLayout
//import android.widget.TextView
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProviders
//import cabily.handyforall.dinedoo.R
//import com.bumptech.glide.Glide
//import com.bumptech.glide.request.target.CustomTarget
//import com.bumptech.glide.request.transition.Transition
//import com.cabilyhandyforalldinedoo.chd.Backgroundservices.commonapifetchservice
//import com.cabilyhandyforalldinedoo.chd.EventBusConnection.IntentServiceResult
//import com.cabilyhandyforalldinedoo.chd.SessionManagerPackage.SessionManager
//import com.cabilyhandyforalldinedoo.chd.commonutils.AppUtils
//import com.cabilyhandyforalldinedoo.chd.commonutils.Constants
//import com.cabilyhandyforalldinedoo.chd.ui.registeration.CountryCodeSelection
//import com.cabilyhandyforalldinedoo.chd.ui.registeration.documentpagetwo
//import com.google.android.material.bottomsheet.BottomSheetDialog
//import com.zeugmasolutions.localehelper.LocaleAwareCompatActivity
//import kotlinx.android.synthetic.main.editprofile.*
//import org.greenrobot.eventbus.EventBus
//import org.greenrobot.eventbus.Subscribe
//import org.greenrobot.eventbus.ThreadMode
//import org.json.JSONObject
//import java.io.ByteArrayOutputStream
//import java.io.File
//import java.text.SimpleDateFormat
//import java.util.*
//class editdriverdetails : LocaleAwareCompatActivity()
//{
//    //variable Decalaration
//    private lateinit var mContext: Activity
//    lateinit var mViewModel: editprofileViewModel
//    private val PICK_CAMERA_IMAGE = 1
//    private val PICK_GALLERY_IMAGE = 2
//    private val gallerypermisssioncode = 999
//    private val camerapermisssioncode = 888
//    var  storebase64string:String=""
//    var  senddob:String=""
//    private val COUNTRYCODEREQUEST = 113
//    var defaultflag:String = "91,IN"
//    var selectedcountrycode:String = ""
//    var selectedsllocation:String = ""
//    var genderprefer = "male"
//    override fun onCreate(savedInstanceState: Bundle?)
//    {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.editprofile)
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//        mContext = this@editdriverdetails
//        //strict mode policy for image
//        val builder = StrictMode.VmPolicy.Builder()
//        StrictMode.setVmPolicy(builder.build())
//        initViewModel()
//        AppUtils.hideKeyboard(mContext, firstname!!)
//        //clickables
//        close.setOnClickListener {
//            AppUtils.hideKeyboard(mContext, firstname!!)
//            finish()
//        }
//        choosecountry.setOnClickListener {
//            countryclassintent()
//        }
//        dob.setOnClickListener {
//                mViewModel.returndob(mContext)
//        }
//        profileimage.setOnClickListener {
//            AppUtils.hideKeyboard(mContext, firstname!!)
//            removebottomsheet()
//        }
//    }
//    fun mobileupdate(view:View)
//    {
//        AppUtils.hideKeyboard(mContext, firstname!!)
//        movetomobileupdate()
//    }
//    fun emailupdate(view:View)
//    {
//        AppUtils.hideKeyboard(mContext, firstname!!)
//        emailupdate()
//    }
//    fun submitclick(view:View)
//    {
//        AppUtils.hideKeyboard(mContext, firstname!!)
//        mViewModel.getvalidationResult(mContext,firstname.text.toString(),lastname.text.toString(),dob.text.toString(),address.text.toString(),choosecountry!!.text.toString(),state.text.toString(),city.text.toString(),"",zipcode.text.toString())
//    }
//    fun editdoc(view:View)
//    {
//        loader.visibility = View.VISIBLE
//        val serviceClass = commonapifetchservice::class.java
//        val intent = Intent(mContext, serviceClass)
//        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.getdriverdocument))
//        mContext.startService(intent)
//    }
//    private fun initViewModel()
//    {
//        radioSex?.setOnCheckedChangeListener { group, checkedId ->
//            genderprefer = if (R.id.radioMale == checkedId) "male" else "female"
//        }
//        mViewModel = ViewModelProviders.of(this).get(editprofileViewModel::class.java)
//        mViewModel.getdriverdetails(mContext)
//        mViewModel.countrycodeobservervalue().observe(this, Observer {
//            formvalidation(it)
//        })
//        mViewModel.updatemailidobserver().observe(this, Observer {
//            emailid.text = it
//        })
//        mViewModel.getdriverbasicdetailsobserver().observe(this, Observer {
//
//
//            storebase64string= it.driverimage.toString()
//
//            firstname.setText(it.driverfirstname.toString())
//            lastname.setText(it.driverlastname.toString())
//            emailid.setText(it.driveremailid.toString())
//            dob.setText(it.driverdob.toString())
//            address.setText(it.driveraddress.toString())
//            choosecountry.setText(it.drivercountry.toString())
//            state.setText(it.driverstate.toString())
//            city.setText(it.drivercity.toString())
//            zipcode.setText(it.driverzipcode.toString())
//            mobilecodewithnumber.setText(it.mobilecode.toString() + " "+it.mobilenumber.toString())
//
//            genderprefer=it.drivergender.toString()
//
//            if(it.drivergender.equals("male"))
//                radioMale.isChecked = true
//            else
//                radioFemale.isChecked = true
//
//            var driverimagepath: String = it.driverimage.toString()
//            if (!driverimagepath.startsWith("http")) {
//                driverimagepath = getString(R.string.amaozonurlprofileimage) + it.driverimage.toString()
//            }
//
//            Glide.with(mContext)
//                    .asBitmap()
//                    .load(driverimagepath!!)
//                    .into(object : CustomTarget<Bitmap>() {
//                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                            runOnUiThread {
//                                profileimage.setImageBitmap(resource)
//                            }
//                        }
//
//                        override fun onLoadCleared(placeholder: Drawable?) {
//                        }
//                    })
//        })
//        mViewModel.countryselectedobservervalue().observe(this, Observer {
//            if(it != null)
//              choosecountry.setText(it)
//        })
//        mViewModel.updatemobilenoobserver().observe(this, Observer {
//            if(it != null)
//                mobilecodewithnumber.setText(it)
//
//        })
//        mViewModel.countryselectedwithcodeobservervalue().observe(this, Observer {
//            if(it != null)
//                selectedcountrycode= it
//        })
//        mViewModel.movenextobserver().observe(this, Observer {
//            documentthree()
//        })
//        mViewModel.errormessageobserver().observe(this, Observer {
//            AppUtils.commonerrorsheet(mContext,getString(R.string.failed),it)
//        })
//        mViewModel.imagebase64observervalue().observe(this, Observer {
//            imagesetprocess(it)
//        })
//        mViewModel.dobdateobserver().observe(this, Observer {
//            dob.text=it
//        })
//        mViewModel.dobdateobserver1().observe(this, Observer {
//           senddob=it
//        })
//    }
//    fun imagesetprocess(it:String)
//    {
//        val decodedString = Base64.decode(it, Base64.DEFAULT)
//        val decodedImage = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
//        if(it.length>2)
//            profileimage.setImageBitmap(decodedImage)
//    }
//    fun formvalidation(it:String)
//    {
//        if(it.equals("1"))
//        {
//            commomerror(firstname,getString(R.string.firstshouldnotbeempty))
//        }
//        else if(it.equals("2"))
//        {
//            commomerror(lastname,getString(R.string.lastshouldnotbeempty))
//        }
//        else if(it.equals("3"))
//        {
//            commomerror(emailid,getString(R.string.emailshouldnotbeempty))
//        }
//        else if(it.equals("4"))
//        {
//            commomerror(emailid,getString(R.string.emailisinvalid))
//        }
//        else if(it.equals("5"))
//        {
//            AppUtils.commonerrorsheet(mContext,getString(R.string.failed),getString(R.string.dobshouldnotbeempty))
//        }
//        else if(it.equals("7"))
//        {
//            commomerror(address,getString(R.string.addreee_empty))
//        }
//        else if(it.equals("8"))
//        {
//            AppUtils.commonerrorsheet(mContext,getString(R.string.failed),getString(R.string.country_empty))
//        }
//        else if(it.equals("9"))
//        {
//            commomerror(state,getString(R.string.state_empty))
//        }
//        else if(it.equals("10"))
//        {
//            commomerror(city,getString(R.string.city_empty))
//        }
//        else if(it.equals("11"))
//        {
//            commomerror(zipcode,getString(R.string.zipcode_empty))
//        }
//       else if(it.equals("12"))
//        {
//            if(storebase64string.equals(""))
//            {
//                AppUtils.commonerrorsheet(mContext,getString(R.string.failed),getString(R.string.upload_profile_photo))
//            }
//            else
//            {
//
//                if(loader.visibility != View.VISIBLE)
//                {
//
//                    mViewModel.splitresponse(mContext,firstname.text.toString(),lastname.text.toString(),emailid.text.toString(),dob.text.toString(),genderprefer,address.text.toString(),choosecountry.text.toString(),state.text.toString(),city.text.toString(),selectedsllocation,zipcode.text.toString(),storebase64string)
//                    loader.visibility = View.VISIBLE
//
//                    var mSessionManager: SessionManager? = null
//                    mSessionManager = SessionManager(mContext)
//                    var params = HashMap<String, String>()
//
//                    val firstname =mSessionManager!!.getUserDetails()!![mSessionManager!!.first_name]!!
//                    val last_name =mSessionManager!!.getUserDetails()!![mSessionManager!!.last_name]!!
//                    val email =mSessionManager!!.getUserDetails()!![mSessionManager!!.email]!!
//                    val dob =mSessionManager!!.getUserDetails()!![mSessionManager!!.dob]!!
//                    val gender =mSessionManager!!.getUserDetails()!![mSessionManager!!.gender]!!
//                    val driver_image =mSessionManager!!.getUserDetails()!![mSessionManager!!.driver_image]!!
//
//                    val address = mSessionManager!!.getUserDetails()!![mSessionManager!!.address]!!
//                    val zipcode = mSessionManager!!.getUserDetails()!![mSessionManager!!.zipcode]!!
//                    val countryid = mSessionManager!!.getUserDetails()!![mSessionManager!!.countryid]!!
//                    val state = mSessionManager!!.getUserDetails()!![mSessionManager!!.state]!!
//                    val city = mSessionManager!!.getUserDetails()!![mSessionManager!!.city]!!
//                    val fcmkey = mSessionManager!!.getfcmkey()
//
//                    params.put("driver_id", mSessionManager!!.getDriverId())
//                    params.put("first_name", firstname)
//                    params.put("last_name", last_name)
//                    params.put("photo", driver_image)
//
//
//                    var spf = SimpleDateFormat("dd/MM/yyyy")
//                    val newDate: Date = spf.parse(dob)
//                    spf = SimpleDateFormat("yyyy-MM-dd")
//                    var date = spf.format(newDate)
//                    params.put("dob", date.toString())
//
//
//                    params.put("gender", gender)
//                    params.put("address", address)
//                    params.put("country", countryid)
//                    params.put("state", state)
//                    params.put("city", city)
//                    params.put("zipcode", zipcode)
//
//
//
//
//
//
//                    val serviceClass = commonapifetchservice::class.java
//                    val intent = Intent(mContext, serviceClass)
//                    intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.basicdriverprofileupdate))
//                    intent.putExtra("params", params)
//                    mContext.startService(intent)
//                }
//            }
//        }
//    }
//    fun commomerror(firstname: TextView,msg:String)
//    {
//        var stringbuild: SpannableStringBuilder =  SpannableStringBuilder(msg)
//        firstname.setError(stringbuild)
//    }
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
//            ActivityCompat.requestPermissions(mContext, arrayOf(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE),camerapermisssioncode)
//        }
//        else
//        {
//           cameraimage()
//        }
//    }
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
//        }
//    }
//    @SuppressLint("MissingPermission")
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
//    {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == Activity.RESULT_OK)
//        {
//            if (requestCode == PICK_GALLERY_IMAGE)
//            {
//                if(data != null)
//                {
//                    var picturePath = ""
//                    var selectedImageUri: Uri = data!!.getData()
//                    picturePath = getPath(applicationContext, selectedImageUri)
//                    var actualImage: File = File(picturePath)
//                    mViewModel.onImagecompress(mContext,actualImage,loader)
//                }
//            }
//           else if (requestCode == PICK_CAMERA_IMAGE)
//            {
//                if(data != null)
//                {
//                    val extras = data?.getExtras()
//                    val imageBitmap = extras?.get("data") as Bitmap
//                    var baos:ByteArrayOutputStream =  ByteArrayOutputStream()
//                    imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
//                    val b = baos.toByteArray()
//                    var imageEncoded:String = Base64.encodeToString(b, Base64.DEFAULT)
//                    imagesetprocess(imageEncoded)
//                    mViewModel.onImagecompresscamera(mContext,imageBitmap,loader)
//                }
//            }
//            else if (requestCode == COUNTRYCODEREQUEST)
//            {
//                if(data != null)
//                {
//                    defaultflag = data?.getStringExtra(Constants.INTENT_OBJECT).toString()
//                    mViewModel.defaultflagprocessor(mContext,defaultflag)
//                }
//            }
//        }
//
//    }
//    fun errormess(heading:String,name:String)
//    {
//        val bottomSheetDialog =  BottomSheetDialog(this)
//        val view = getLayoutInflater().inflate(R.layout.errorlisten, null);
//        bottomSheetDialog.setContentView(view)
//
//        val subcontent = view.findViewById(R.id.subcontent) as TextView
//        val title = view.findViewById(R.id.title) as TextView
//
//        title.setText(heading)
//        subcontent.setText(name)
//
//        bottomSheetDialog.show()
//    }
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
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun doThis(intentServiceResult: IntentServiceResult)
//    {
//        var finalresponse: String = intentServiceResult.resultValue
//        loader.visibility = View.INVISIBLE
//        var apiName: String = intentServiceResult.apiName
//
//        if (apiName.equals(getString(R.string.amazonimageuploaded)))
//        {
//            if (finalresponse != "failed")
//            {
//              storebase64string=finalresponse
//                var changeprofileimage = getString(R.string.amaozonurlprofileimage)+finalresponse!!
//
//                Glide.with(mContext)
//                        .asBitmap()
//                        .load(changeprofileimage)
//                        .into(object : CustomTarget<Bitmap>() {
//                            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                                profileimage.setImageBitmap(resource)
//                            }
//                            override fun onLoadCleared(placeholder: Drawable?) {
//                            }
//                        })
//            }
//            else
//                AppUtils.commonerrorsheet(mContext,getString(R.string.failed),getString(R.string.profailed))
//        }
//        else   if (apiName.equals(getString(R.string.basicdriverprofileupdate)))
//        {
//            loader.visibility = View.INVISIBLE
//            if (finalresponse != "failed")
//            {
//                val response_json_object = JSONObject(finalresponse)
//                try
//                {
//                    val status = response_json_object.getString("status")
//                    if (status.equals("1"))
//                    {
//                        finish()
//                    }
//                    else
//                    {
//                        val response = response_json_object.getString("message")
//                        errormess(getString(R.string.failed),response)
//                    }
//                }
//                catch (e: Exception)
//                {
//                }
//            }
//            else
//            {
//                AppUtils.commonerrorsheet(mContext,getString(R.string.failed),getString(R.string.failedproblem))
//            }
//        }
//        else  if (apiName.equals(getString(R.string.getdriverdocument)))
//        {
//            loader.visibility = View.GONE
//            var response: String = intentServiceResult.resultValue
//            if (response != "failed")
//            {
//                mViewModel.deletedb(mContext)
//                mViewModel.splitdocumentinfo(applicationContext,response)
//            }
//            else
//            {
//                AppUtils.commonerrorsheet(mContext,getString(R.string.failed),getString(R.string.failedproblem))
//            }
//        }
//    }
//    override fun onResume()
//    {
//        super.onResume()
//        if(!EventBus.getDefault().isRegistered(this))
//        {
//            EventBus.getDefault().register(this)
//        }
//        mViewModel.getdrivermobileno(mContext)
//        mViewModel.getemailid(mContext)
//    }
//    override fun onDestroy()
//    {
//        super.onDestroy()
//        if(EventBus.getDefault().isRegistered(this))
//        {
//            EventBus.getDefault().unregister(this);
//        }
//    }
//    fun countryclassintent()
//    {
//        val intent_book_now = Intent(mContext, CountryCodeSelection::class.java)
//        startActivityForResult(intent_book_now, COUNTRYCODEREQUEST)
//    }
//    fun removebottomsheet()
//    {
//        val bottomSheetDialog =  BottomSheetDialog(this)
//        val view = getLayoutInflater().inflate(R.layout.includechooseselection, null);
//        bottomSheetDialog.setContentView(view)
//        val fromcamera = view.findViewById(R.id.fromcamera) as LinearLayout
//        val fromgallery = view.findViewById(R.id.fromgallery) as LinearLayout
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
//    fun documentthree()
//    {
//        val intent_otppage = Intent(mContext, documentpagetwo::class.java)
//        intent_otppage.putExtra("getfromadd", "0")
//        startActivity(intent_otppage)
//    }
//    fun movetomobileupdate()
//    {
//        val intent_otppage = Intent(mContext, mobileandemailupdate::class.java)
//        startActivity(intent_otppage)
//    }
//    fun emailupdate()
//    {
//        val intent_otppage = Intent(mContext, emailseprateupdate::class.java)
//        startActivity(intent_otppage)
//    }
//
//}