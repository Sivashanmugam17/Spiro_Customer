//package com.cabilyhandyforalldinedoo.chd.ui.registeration
//
//import android.Manifest
//import android.annotation.SuppressLint
//import android.app.Activity
//import android.content.Context
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.graphics.Bitmap
//import android.graphics.BitmapFactory
//import android.net.Uri
//import android.os.Bundle
//import android.os.StrictMode
//import android.provider.MediaStore
//import android.text.SpannableStringBuilder
//import android.util.Base64
//import android.util.Log
//import android.view.View
//import android.widget.*
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProviders
//import cabily.handyforall.dinedoo.R
//import com.cabilyhandyforalldinedoo.chd.Backgroundservices.commonapifetchservice
//import com.cabilyhandyforalldinedoo.chd.EventBusConnection.IntentServiceResult
//import com.cabilyhandyforalldinedoo.chd.SessionManagerPackage.SessionManager
//import com.cabilyhandyforalldinedoo.chd.ViewModelRegisterModule.registerpagefourViewModel
//import com.cabilyhandyforalldinedoo.chd.commonutils.AppUtils
//import com.cabilyhandyforalldinedoo.chd.commonutils.Constants
//import com.cabilyhandyforalldinedoo.chd.ui.MainPage.turnlocationonandintenet
//import com.google.android.material.bottomsheet.BottomSheetDialog
//import kotlinx.android.synthetic.main.documentpageone.*
////import kotlinx.android.synthetic.main.registerpagefour.*
////import kotlinx.android.synthetic.main.registerpagefour.close
////import kotlinx.android.synthetic.main.registerpagefour.loader
////import kotlinx.android.synthetic.main.registerpagefour.locationtype
////import kotlinx.android.synthetic.main.registerpagefour.next
////import kotlinx.android.synthetic.main.registerpagefour.nextlayout
////import kotlinx.android.synthetic.main.registerpagefour.privacy_check
//import kotlinx.android.synthetic.main.registerpagetwo.*
//import org.greenrobot.eventbus.EventBus
//import org.greenrobot.eventbus.Subscribe
//import org.greenrobot.eventbus.ThreadMode
//import org.json.JSONObject
//import java.io.ByteArrayOutputStream
//import java.io.File
//import java.text.SimpleDateFormat
//import java.util.*
//
//
//class registerpagefour : LocaleAwareCompatActivity()
//{
//    //variable Decalaration
//    private lateinit var mContext: Activity
//    lateinit var mViewModel: registerpagefourViewModel
//    var  mobilenovalue:String=""
//    var  codenovalue:String=""
//    private val PICK_CAMERA_IMAGE = 1
//    private val PICK_GALLERY_IMAGE = 2
//    private val gallerypermisssioncode = 999
//    private val camerapermisssioncode = 888
//    var  storebase64string:String=""
//    var  senddob:String=""
//    private val COUNTRYCODEREQUEST = 113
//    var defaultflag:String = "91,IN"
//    var selectedcountrycode:String = ""
//    var selectedtype:String = ""
//    var selectedtypeid:String = ""
//    var selectedsllocation:String = ""
//    private val LOCATIONCODEEREQUEST = 114
//    lateinit var getvalueromprevious:String
//    var locationid:String = ""
//    private var mSessionManager: SessionManager ?= null
//    var genderprefer = "male"
//    var check:String="0"
//    var click:String="0"
//
//    override fun onCreate(savedInstanceState: Bundle?)
//    {
//        super.onCreate(savedInstanceState)
////        setContentView(R.layout.registerpagefour)
//        mContext = this@registerpagefour
//
//        //strict mode policy for image
//        val builder = StrictMode.VmPolicy.Builder()
//        StrictMode.setVmPolicy(builder.build())
//        mSessionManager = SessionManager(applicationContext)
//
//        getExtraValue()
//        initViewModel()
//        vehicle_select()
//        text_changesListeners()
//
//
//        AppUtils.hideKeyboard(mContext, firstname!!)
//
//        //clickables
//        close.setOnClickListener {
//            AppUtils.hideKeyboard(mContext, firstname!!)
//            finish()
//        }
//
//        choosecountry.setOnClickListener {
//            countryclassintent()
//        }
//        vehicle_spinnericon.setOnClickListener{
//            vehicle_spinner.performClick()
//        }
//        locationtype.setOnClickListener {
//            vehicledocumentselect(LOCATIONCODEEREQUEST, "1")
//        }
//
//
//
//
//        nextlayout.setOnClickListener {
//            AppUtils.hideKeyboard(mContext, firstname!!)
//            mViewModel.getvalidationResult(mContext, firstname.text.toString(), lastname.text.toString(), emailid.text.toString(), dob.text.toString(), address.text.toString(), choosecountry!!.text.toString(), state.text.toString(), city.text.toString(), "", zipcode.text.toString())
//        }
//
//
//        next.setOnClickListener {
//            AppUtils.hideKeyboard(mContext, firstname!!)
//            mViewModel.getvalidationResult(mContext, firstname.text.toString(), lastname.text.toString(), emailid.text.toString(), dob.text.toString(), address.text.toString(), choosecountry!!.text.toString(), state.text.toString(), city.text.toString(), "", zipcode.text.toString())
//        }
//
//        dob.setOnClickListener {
//                mViewModel.returndob(mContext)
//        }
//
//        profileimage.setOnClickListener {
//            AppUtils.hideKeyboard(mContext, firstname!!)
//            removebottomsheet()
//        }
//
//
//
//
//    }
//
//    fun vehicledocumentselect(requestCode: Int, putextrastring: String)
//    {
//        if(loader.visibility == View.VISIBLE)
//        {
//            AppUtils.commonerrorsheet(mContext, getString(R.string.failed), getString(R.string.waittillimageupload))
//        }
//        else
//        {
//            val intent2 = Intent(mContext, VehicleDetailsSelection::class.java)
//            intent2.putExtra("vehicled", putextrastring)
////            intent2.putExtra("locationid",locationid)
////            intent2.putExtra("categoryid",categoryid)
////            intent2.putExtra("vehicletypeid",vehicletypeid)
////            intent2.putExtra("makeid",makeid)
////            intent2.putExtra("modelid",modelid)
////            intent2.putExtra("skipingid","")
//            startActivityForResult(intent2, requestCode)
//        }
//    }
//
//    private fun getExtraValue()
//    {
//        mobilenovalue = intent.getStringExtra("mobileno").toString()
//        codenovalue = intent.getStringExtra("code").toString()
//    }
//    private fun initViewModel()
//    {
//        radioSex?.setOnCheckedChangeListener { group, checkedId ->
//            genderprefer += if (R.id.radioMale == checkedId) "male" else "female"
//        }
//
//        mViewModel = ViewModelProviders.of(this).get(registerpagefourViewModel::class.java)
//
//        mViewModel.countrycodeobservervalue().observe(this, Observer {
//            formvalidation(it)
//        })
//
//        mViewModel.countryselectedobservervalue().observe(this, Observer {
//            if (it != null)
//                choosecountry!!.text = it
//        })
//
//        mViewModel.countryselectedwithcodeobservervalue().observe(this, Observer {
//            if (it != null)
//                selectedcountrycode = it
//        })
//
//        mViewModel.movenextobserver().observe(this, Observer {
//            if (it.equals("1")) {
//
////                successdialog(getString(R.string.success),getString(R.string.thanks_for_registering_with_us_our_admin_will_be_verfying_your_document))
//                if (selectedtype.equals("2")) {
//                    documentpagetwo()
//                } else {
//                    documentpageone()
//                }
//            }
//        })
//
//
//
//        mViewModel.imagebase64observervalue().observe(this, Observer {
//            imagesetprocess(it)
//        })
//        mViewModel.dobdateobserver().observe(this, Observer {
//            dob.text = it
//        })
//        mViewModel.dobdateobserver1().observe(this, Observer {
//            senddob = it
//        })
//        mViewModel.success().observe(this, Observer {
//
//            if (selectedtype.equals("2")) {
//                documentpagetwo()
//            } else {
//                documentpageone()
//
//            }
//
//        })
//
//
//    }
//
//    private fun text_changesListeners() {
//        privacy_check.setBackgroundResource(R.drawable.ic_rectange)
//
//
//
//
//
//        privacy_check.setOnClickListener {
//
//            if (click.equals("0")){
//                privacy_check.setBackgroundResource(R.drawable.ic_check_box)
//                check="1"
//                click="1"
//            }else{
//                privacy_check.setBackgroundResource(R.drawable.ic_rectange)
//                click="0"
//                check="0"
//            }
//
//
//        }
//
//    }
//
//
//    private fun vehicle_select() {
//        var vehicle=ArrayList<String>()
////             vehicle.addAll(VehicleDetailsViewModel.myCategorytype)
//        vehicle.add("")
//        vehicle.add("Bike")
//        vehicle.add("E-Bike")
////        vehicle.add("Auto")
//        if (vehicle_spinner != null) {
//            val adapterd = vehicletypeCustomAdapter(this, vehicle)
//            vehicle_spinner.adapter = adapterd
//            vehicle_spinner.onItemSelectedListener = object :
//                    AdapterView.OnItemSelectedListener {
//                override fun onItemSelected(parent: AdapterView<*>,
//                                            view: View, position: Int, id: Long) {
//                    selectedtype= position.toString()
//                    if (selectedtype.equals("1")){
//                        selectedtypeid="1628945980-0284-4719-8c09-19faf6ab686d"
//                    }
//                    if (selectedtype.equals("2")){
//                        selectedtypeid="1628945260-816f-4dd7-9ac5-71f70e357e8e"
//                    }
//
//                    mSessionManager?.setbiketype(selectedtype)
//
//
//
//                }
//                override fun onNothingSelected(parent: AdapterView<*>) {
//                    // write code to perform some action
//                }
//            }
//        }
//    }
//
//    fun imagesetprocess(it: String)
//    {
//        val decodedString = Base64.decode(it, Base64.DEFAULT)
//        val decodedImage = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
//        if(it.length>2)
//            profileimage.setImageBitmap(decodedImage)
//    }
//    fun formvalidation(it: String)
//    {
//        if(it.equals("1"))
//        {
//            commomerror(firstname, getString(R.string.firstshouldnotbeempty))
//        }
//        else if(it.equals("2"))
//        {
//            commomerror(lastname, getString(R.string.lastshouldnotbeempty))
//        }
//        else if(it.equals("3"))
//        {
//            commomerror(emailid, getString(R.string.emailshouldnotbeempty))
//        }
//        else if(it.equals("4"))
//        {
//            commomerror(emailid, getString(R.string.emailisinvalid))
//        }
//        else if(it.equals("5"))
//        {
//
//            AppUtils.commonerrorsheet(mContext, getString(R.string.failed), getString(R.string.dobshouldnotbeempty))
//        }
//
//        else if(it.equals("7"))
//        {
//            commomerror(address, getString(R.string.addreee_empty))
//        }
//        else if(it.equals("8"))
//        {
//            AppUtils.commonerrorsheet(mContext, getString(R.string.failed), getString(R.string.country_empty))
//        }
//        else if(it.equals("9"))
//        {
//            commomerror(state, getString(R.string.state_empty))
//        }
//        else if(it.equals("10"))
//        {
//            commomerror(city, getString(R.string.city_empty))
//        }
//        else if(it.equals("11"))
//        {
//            commomerror(zipcode, getString(R.string.zipcode_empty))
//        }
//       else if(it.equals("12"))
//        {
//            if(storebase64string.equals("") || check.equals("0")) {
//                if (check.equals("0")){
//                    AppUtils.commonerrorsheet(mContext, getString(R.string.failed), getString(R.string.Please_Check))
//
//                }else{
//                    AppUtils.commonerrorsheet(mContext, getString(R.string.failed), getString(R.string.upload_profile_photo))
//
//                }
//
//            }
//            else
//            {
//
//                if(loader.visibility != View.VISIBLE)
//                {
//
//                    mViewModel.splitresponse(mContext, firstname.text.toString(), lastname.text.toString(), emailid.text.toString(), dob.text.toString(), genderprefer, address.text.toString(), choosecountry.text.toString(), state.text.toString(), city.text.toString(), selectedsllocation, zipcode.text.toString(), mobilenovalue, codenovalue, storebase64string)
//                    loader.visibility = View.VISIBLE
//                    //mViewModel.checkemailexist(mContext,emailid.text.toString())
//
//                    var mSessionManager: SessionManager? = null
//                    mSessionManager = SessionManager(mContext)
//                    var params = HashMap<String, String>()
//
//                    val firstname =mSessionManager!!.getUserDetails()!![mSessionManager!!.first_name]!!
//                    val last_name =mSessionManager!!.getUserDetails()!![mSessionManager!!.last_name]!!
//                    val email =mSessionManager!!.getUserDetails()!![mSessionManager!!.email]!!
//                    val mobile_number =mSessionManager!!.getUserDetails()!![mSessionManager!!.mobilenumber]!!
//                    val dail_code =mSessionManager!!.getUserDetails()!![mSessionManager!!.mobilecode]!!
//                    val dob =mSessionManager!!.getUserDetails()!![mSessionManager!!.dob]!!
//                    val gender =mSessionManager!!.getUserDetails()!![mSessionManager!!.gender]!!
//                    val driver_image =mSessionManager!!.getUserDetails()!![mSessionManager!!.driver_image]!!
//                    val city = mSessionManager!!.getUserDetails()!![mSessionManager!!.city]!!
//                    val address = mSessionManager!!.getUserDetails()!![mSessionManager!!.address]!!
//                    val zipcode = mSessionManager!!.getUserDetails()!![mSessionManager!!.zipcode]!!
//                    val countryid = mSessionManager!!.getUserDetails()!![mSessionManager!!.countryid]!!
//                    val state = mSessionManager!!.getUserDetails()!![mSessionManager!!.state]!!
//                    val fcmkey = mSessionManager!!.getfcmkey()
//
//                    if(dail_code.startsWith("+"))
//                        params.put("dial_code", dail_code)
//                    else
//                        params.put("dial_code", "+" + dail_code)
//
//                    params.put("phone_number", mobile_number)
//                    params.put("first_name", firstname)
//                    params.put("last_name", last_name)
//                    params.put("email", email)
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
//                    params.put("referal_code", "")
//                    params.put("address", address)
////                    params.put("media", "")
////                    params.put("media_id", "")
//
//
//
//                    params.put("address", address)
//                    params.put("country", countryid)
//                    params.put("state", state)
//                    params.put("city", city)
//                    params.put("zipcode", zipcode)
//                    params.put("country_code", "IN")
//
//
//                    params.put("location", "1629097928-bd3e-416a-a80f-8c3e92a2f02f")
//                    params.put("category", selectedtypeid)
//
//
//
//
//                    val serviceClass = commonapifetchservice::class.java
//                    val intent = Intent(mContext, serviceClass)
//                    intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.finaldocument))
//                    intent.putExtra("params", params)
//                    mContext.startService(intent)
//                }
//            }
//        }
//    }
//    fun documentpageone()
//    {
//        val intent2 = Intent(mContext, documentpageone::class.java)
//        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        startActivity(intent2)
//    }
//    fun documentpagetwo()
//    {
//        val intent2 = Intent(mContext, documentpagetwo::class.java)
//        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        startActivity(intent2)
//    }
//
//    fun commomerror(firstname: EditText, msg: String)
//    {
//        firstname.requestFocus();
//        var stringbuild: SpannableStringBuilder =  SpannableStringBuilder(msg)
//        firstname.setError(stringbuild)
//    }
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
//            ActivityCompat.requestPermissions(mContext, arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), camerapermisssioncode)
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
//                if ((grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//                    cameraimage()
//                }
//            }
//        }
//    }
//    @SuppressLint("MissingPermission")
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
//    {
//        super.onActivityResult(requestCode, resultCode, data)
//        Log.d("djh34hjhhjdjjdd", resultCode.toString())
//        Log.d("chxghchxs", data.toString())
//
//        if (resultCode == Activity.RESULT_OK)
//        {
//            if (requestCode == PICK_GALLERY_IMAGE)
//            {
//                if(data != null)
//                {
//                    var picturePath = ""
//                    var selectedImageUri: Uri = data!!.getData()!!
//                    picturePath = getPath(applicationContext, selectedImageUri)
//                    var actualImage: File = File(picturePath)
//                    Log.d("dfdstgdg", actualImage.toString())
//
//                    mViewModel.onImagecompress(mContext, actualImage, loader, selectedImageUri)
//                }
//            }
//           else if (requestCode == PICK_CAMERA_IMAGE)
//            {
//                if(data != null)
//                {
//
//                    val extras = data?.getExtras()
//                    val imageBitmap = extras?.get("data") as Bitmap
//                    Log.d("fdftgfgfg", imageBitmap.toString())
//                    var myUri =  mViewModel.getImageUri(mContext,imageBitmap)
//
//
//                    var baos:ByteArrayOutputStream =  ByteArrayOutputStream()
//                    imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
//                    val b = baos.toByteArray()
//                    var imageEncoded:String = Base64.encodeToString(b, Base64.DEFAULT)
//                    imagesetprocess(imageEncoded)
//                    mViewModel.onImagecompresscamera(mContext, imageBitmap, loader, myUri!!)
//                }
//
//
//            }
//            else if (requestCode == LOCATIONCODEEREQUEST)
//            {
//                if(data != null)
//                {
//                    getvalueromprevious = data?.getStringExtra(Constants.INTENT_OBJECT).toString()
//                    val splitvalues = getvalueromprevious.split(",")
//                    locationid = splitvalues[0].trim()
//                    val value = splitvalues[1].trim()
//                    locationtype.text = value
////            locationsuccess.visibility= View.VISIBLE
//                    mViewModel.chooselocation(mContext, locationid, value)
//                }
//            }
//        }else if (resultCode == Activity.RESULT_FIRST_USER)
//        {
//            if(data != null)
//            {
//                Log.d("cxhghcghvgh", "chjhjchhjjcjc")
//
//                defaultflag = data?.getStringExtra(Constants.INTENT_OBJECT).toString()
//                mViewModel.defaultflagprocessor(mContext, defaultflag)
//            }
//        }
//        else if (requestCode == LOCATIONCODEEREQUEST)
//        {
//            if (data!=null) {
//                getvalueromprevious = data?.getStringExtra(Constants.INTENT_OBJECT).toString()
//                val splitvalues = getvalueromprevious.split(",")
//                locationid = splitvalues[0].trim()
//                val value = splitvalues[1].trim()
//                locationtype.text = value
////            locationsuccess.visibility= View.VISIBLE
//                mViewModel.chooselocation(mContext, locationid, value)
//            }
//        }
//
//    }
//
//    fun errormess(heading: String, name: String)
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
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun doThis(intentServiceResult: IntentServiceResult)
//    {
//        var finalresponse: String = intentServiceResult.resultValue
//        loader.visibility = View.INVISIBLE
//        var apiName: String = intentServiceResult.apiName
//        if (apiName.equals(getString(R.string.emailcheckk)))
//        {
//            if (finalresponse == "1")
//            {
//                mViewModel.splitresponse(mContext, firstname.text.toString(), lastname.text.toString(), emailid.text.toString(), dob.text.toString(), genderprefer, address.text.toString(), choosecountry.text.toString(), state.text.toString(), city.text.toString(), selectedsllocation, zipcode.text.toString(), mobilenovalue, codenovalue, storebase64string)
//            }
//        }
//        else if (apiName.equals(getString(R.string.amazonimageuploaded)))
//        {
//            if (finalresponse != "failed")
//            {
//              storebase64string=finalresponse
//                Log.d("dgdfgdg", storebase64string)
////              Toast.makeText(applicationContext,getString(R.string.save_profilepic),Toast.LENGTH_LONG).show()
//            }
//            else
//                AppUtils.commonerrorsheet(mContext, getString(R.string.failed), getString(R.string.profailed))
//        }
//        else   if (apiName.equals(getString(R.string.finaldocument)))
//        {
//            loader.visibility = View.INVISIBLE
//            if (finalresponse != "failed")
//            {
//                val response_json_object = JSONObject(finalresponse)
//                try
//                {
//                    val status = response_json_object.getString("status")
//                    if (status.equals("0"))
//                    {
//                        val response = response_json_object.getString("response")
//                        errormess(getString(R.string.failed), response)
//                    }
//                    if (status.equals("1"))
//                    {
//                        mViewModel.savedriverprofiledata(mContext, finalresponse)
//                    }
//                    else
//                    {
//                        val response = response_json_object.getString("message")
//                        errormess(getString(R.string.failed), response)
//                    }
//                }
//                catch (e: Exception)
//                {
//                }
//            }
//            else
//            {
//                AppUtils.commonerrorsheet(mContext, getString(R.string.failed), getString(R.string.failedproblem))
//            }
//        }
//    }
//
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
//
//
//    fun countryclassintent()
//    {
//        val intent_book_now = Intent(mContext, CountryCodeSelection::class.java)
//        startActivityForResult(intent_book_now, COUNTRYCODEREQUEST)
//    }
//
//
//
//
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
//
//
//    fun successdialog(heading: String, content: String)
//    {
//        val bottomSheetDialog =  BottomSheetDialog(this)
//        val view = getLayoutInflater().inflate(R.layout.successdialog, null);
//        bottomSheetDialog.setContentView(view)
//        bottomSheetDialog.setCancelable(false)
//
//        val title = view.findViewById(R.id.title) as TextView
//        val subcontent = view.findViewById(R.id.subcontent) as TextView
//
//        title.setText(heading)
//        subcontent.setText(content)
//
//        val backtologinlayout = view.findViewById(R.id.backtologinlayout) as LinearLayout
//        backtologinlayout.visibility = View.GONE
//
//        val done = view.findViewById(R.id.done) as LinearLayout
//        val close = view.findViewById(R.id.close) as LinearLayout
//
//        done.setOnClickListener {
//            bottomSheetDialog.dismiss()
//            documentthree()
//        }
//
//        close.setOnClickListener {
//            bottomSheetDialog.dismiss()
//            documentthree()
//        }
//
//        bottomSheetDialog.show()
//    }
//
//    fun documentthree()
//    {
////        if (selectedtype.equals("2")){
////            documentpagetwo()
////        }else{
////            documentpageone()
////
////        }
//        val intent_otppage = Intent(mContext, turnlocationonandintenet::class.java)
//        intent_otppage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//        intent_otppage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        startActivity(intent_otppage)
//    }
//
//}