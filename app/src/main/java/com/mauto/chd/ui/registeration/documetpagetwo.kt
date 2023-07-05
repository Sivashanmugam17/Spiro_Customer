package com.mauto.chd.ui.registeration

//import android.Manifest
//import android.annotation.SuppressLint
//import android.app.Activity
//import android.content.Context
//import android.content.DialogInterface
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.graphics.Bitmap
//import android.graphics.Color
//import android.net.Uri
//import android.os.Bundle
//import android.os.Environment
//import android.os.StrictMode
//import android.provider.MediaStore
//import android.util.Log
//import android.view.View
//import android.widget.Button
//import android.widget.EditText
//import android.widget.LinearLayout
//import android.widget.TextView
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProviders
//import androidx.recyclerview.widget.LinearLayoutManager
//import cabily.handyforall.dinedoo.R
//import com.cabilyhandyforalldinedoo.chd.Backgroundservices.commonapifetchservice
//import com.cabilyhandyforalldinedoo.chd.EventBusConnection.IntentServiceResult
//import com.cabilyhandyforalldinedoo.chd.Modal.tripdetailsModels
//import com.cabilyhandyforalldinedoo.chd.SessionManagerPackage.SessionManager
//import com.cabilyhandyforalldinedoo.chd.ViewModelRegisterModule.documetpagetwoViewModel
//import com.cabilyhandyforalldinedoo.chd.adaptersofchd.documentuploadtwoadapter
//import com.cabilyhandyforalldinedoo.chd.commonutils.AppUtils
//import com.cabilyhandyforalldinedoo.chd.data.steptwodocumentdb.documenttwodoRecord
//import com.cabilyhandyforalldinedoo.chd.ui.MainPage.mianscreenpage
//import com.cabilyhandyforalldinedoo.chd.ui.MainPage.turnlocationonandintenet
//import com.google.android.material.bottomsheet.BottomSheetBehavior
//import com.google.android.material.bottomsheet.BottomSheetDialog
//import kotlinx.android.synthetic.main.documentpageone.*
//import kotlinx.android.synthetic.main.documentpagetwo.*
//import kotlinx.android.synthetic.main.documentpagetwo.close
//import kotlinx.android.synthetic.main.documentpagetwo.loader
//import kotlinx.android.synthetic.main.documentsupload.*
//import org.greenrobot.eventbus.EventBus
//import org.greenrobot.eventbus.Subscribe
//import org.greenrobot.eventbus.ThreadMode
//import org.jetbrains.anko.textColor
//import org.json.JSONObject
//import java.io.File
//import java.text.SimpleDateFormat
//import java.util.*
//import kotlin.math.log
//
//
//class documentpagetwo : LocaleAwareCompatActivity(), documentuploadtwoadapter.TodoEvents
//{
//    private lateinit var todoViewModel: documetpagetwoViewModel
//    private lateinit var mContext: Activity
//    private lateinit var todoAdapter: documentuploadtwoadapter
//    private val gallerypermisssioncode = 999
//    var firstimage:String = ""
//    var secondimage:String = ""
//    var secondimage1:String = ""
//
//    private val PICK_CAMERA_IMAGE = 1
//    private val PICK_GALLERY_IMAGE = 2
//     var nofilefronside:TextView ? =null
//    var nofilebackside:TextView ? =null
//    var expirydateclick: Button?= null
//     var todo: documenttwodoRecord ?= null
//     var bottomSheetDialog:BottomSheetDialog ?= null
//    lateinit var imagenames:String
//     var frontorbacksideupload:String=""
//    private val camerapermisssioncode = 888
//    var hidedocumentofdriver:Int = 0
//    internal lateinit var mSessionManager: SessionManager
//    lateinit var camera_FileUri :Uri
//    var accountnumber: EditText?= null
//    var maccountnumber: String?= null
//    var maccountfirst:String="1"
//
//
//
//    override fun onCreate(savedInstanceState: Bundle?)
//    {
//        super.onCreate(savedInstanceState)
////        setContentView(R.layout.documentpagetwo)
//        mContext = this@documentpagetwo
//        mSessionManager = SessionManager(mContext)
//        getdriverdocument()
//        //strict mode policy for image
//        val builder = StrictMode.VmPolicy.Builder()
//        StrictMode.setVmPolicy(builder.build())
//        Log.d("AuthToken", mSessionManager!!.getApiHeader().toString())
//
//        getextravalue()
//
//        initViewModel()
//        settinguprecyclerview()
//        close.setOnClickListener {
//            finish()
//        }
//    }
//
//    fun getextravalue()
//    {
//        if (intent?.hasExtra("getfromadd")!!)
//        {
//           hidedocumentofdriver = 1
//        }
//    }
//
//    fun getdriverdocument() {
//        val serviceClass = commonapifetchservice::class.java
//        val intent = Intent(mContext, serviceClass)
//        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.profile_driver))
//        mContext.startService(intent)
//    }
//
//    fun settinguprecyclerview()
//    {
//        rv_todo_list.layoutManager = LinearLayoutManager(this)
//        todoAdapter = documentuploadtwoadapter(this, applicationContext)
//        rv_todo_list.adapter = todoAdapter
//    }
//
//
//    fun initViewModel()
//    {
//        //Setting up ViewModel and LiveData
//        todoViewModel = ViewModelProviders.of(this).get(documetpagetwoViewModel::class.java)
//
//        todoViewModel.datesaveobservervalue().observe(this, Observer {
//            todo = documenttwodoRecord(id = todo!!.id, coloumnid = todo!!.coloumnid, coloumnname = todo!!.coloumnname, category = todo!!.category, is_req = todo!!.is_req, is_exp = todo!!.is_exp, notes = todo!!.notes, expirydate = it, imagestore = todo!!.imagestore, imagestore1 = todo!!.imagestore1, uploadstatus = todo!!.uploadstatus, frontandback = todo!!.frontandback, submitedfully = todo!!.submitedfully, document_name = todo!!.document_name, uuid = todo!!.uuid,has_input = todo!!.has_input,account_number = todo!!.account_number)
//            todoViewModel.updateTodo(todo!!)
//            if (bottomSheetDialog != null) {
//                if (bottomSheetDialog!!.isShowing) {
//                    expirydateclick!!.text = it
//                }
//            }
//        })
//
//
//        todoViewModel.movenextobserver().observe(this, Observer {
//            if (it.equals("1")) {
//                successdialog(getString(R.string.success), getString(R.string.thanks_for_registering_with_us_our_admin_will_be_verfying_your_document))
//            }
//        })
//
//
//        todoViewModel.getAllTodoList().observe(this, Observer {
//
//            // Toast.makeText(applicationContext,""+it.size,Toast.LENGTH_LONG).show()
//            if (it.size == 0) {
//                loader.visibility = View.VISIBLE
//                todoViewModel.startsteptwoapi(mContext)
//
//            } else {
//                todoAdapter.setAllTodoItems(it)
//            }
//        })
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun doThis(intentServiceResult: IntentServiceResult)
//    {
//        var finalresponse: String = intentServiceResult.resultValue
//
//        var apiName: String = intentServiceResult.apiName
//        if (apiName.equals(getString(R.string.finaldocumentupload))){
//            mSessionManager.setDocPending("1")
//
//        }
//        if (apiName.equals(getString(R.string.profile_driver))){
//            if (finalresponse != "failed") {
//                val response_json_object = JSONObject(finalresponse)
//                Log.d("sdfbhsbfd", response_json_object.toString())
//                    val status = response_json_object.getString("status")
//                    if (status.equals("1")) {
//                        val response = response_json_object.getJSONObject("response")
//                        val driver_info = response.getJSONObject("driver_info")
//                        val documentsarray2 = driver_info.getJSONArray("documents")
//                        if (documentsarray2.length() > 0) {
//                            for (g in 0 until documentsarray2.length()) {
//                                val response_object_faq2 = documentsarray2.getJSONObject(g)
//
//                                var vehicle_id = response_object_faq2.getString("vehicle_id")
//                                var type = response_object_faq2.getString("type")
//
//                                Log.d("vehicle_id_tem", vehicle_id)
//                                if(type.equals("vehicle")){
//                                    if(vehicle_id.equals("")){
//
//                                    }else{
//                                        mSessionManager.setvehicleid(vehicle_id)
//
//                                    }
//                                }
//
//
//
//
//                            }
//
//
////                        documentcompleted()
//
////                        successdialog(getString(R.string.success),getString(R.string.thanks_for_registering_with_us_our_admin_will_be_verfying_your_document))
//
//                        }
//
//                    }
//
//
//                }
//            }
//        if (apiName.equals(getString(R.string.steptwoapi)))
//        {
//            loader.visibility = View.INVISIBLE
//            if (finalresponse != "failed")
//            {
//                todoViewModel.splitresponse(mContext, finalresponse, hidedocumentofdriver)
//            }
//        }
//        else if (apiName.equals(getString(R.string.finalcalltohomepage)))
//        {
//            loader.visibility = View.INVISIBLE
//            if (finalresponse != "failed")
//            {
//
//                val response_json_object = JSONObject(finalresponse)
//                try
//                {
//                    val status = response_json_object.getString("status")
//                    if (status.equals("1"))
//                    {
//                        val response = response_json_object.getJSONObject("response")
//                        var stage =response.getString("stage")
//                        mSessionManager.setdocumentstage2(stage)
//                        mSessionManager.setdocuments_secondimage("")
//
//
//                        mSessionManager.setverificationtatus("1")
////                        documentcompleted()
//                        documentthree()
//
////                        successdialog(getString(R.string.success),getString(R.string.thanks_for_registering_with_us_our_admin_will_be_verfying_your_document))
//
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
//        }
//        else if (apiName.equals(getString(R.string.amazonimageuploaded)))
//        {
//            if (finalresponse != "failed")
//            {
//                loader.visibility = View.INVISIBLE
//                if(frontorbacksideupload.equals("1"))
//                {
//                    todo = documenttwodoRecord(id = todo!!.id, coloumnid = todo!!.coloumnid, coloumnname = todo!!.coloumnname, category = todo!!.category, is_req = todo!!.is_req, is_exp = todo!!.is_exp, notes = todo!!.notes, expirydate = todo!!.expirydate, imagestore = finalresponse, imagestore1 = todo!!.imagestore1, uploadstatus = getString(R.string.fileadded), frontandback = todo!!.frontandback, submitedfully = todo!!.submitedfully, document_name = finalresponse, uuid = todo!!.uuid,has_input = todo!!.has_input,account_number = todo!!.account_number)
//                    todoViewModel.updateTodo(todo!!)
//
//                    if(bottomSheetDialog != null)
//                    {
//                        if(bottomSheetDialog!!.isShowing)
//                        {
//                            firstimage=finalresponse
//                            Log.d("sdrfggfdsd",finalresponse)
//                            nofilefronside!!.text = getString(R.string.fileadded)
//                            nofilefronside!!.textColor = Color.parseColor("#427fed")
//                        }
//                    }
//                }
//                else  if(frontorbacksideupload.equals("2"))
//                {
//                    todo = documenttwodoRecord(id = todo!!.id, coloumnid = todo!!.coloumnid, coloumnname = todo!!.coloumnname, category = todo!!.category, is_req = todo!!.is_req, is_exp = todo!!.is_exp, notes = todo!!.notes, expirydate = todo!!.expirydate, imagestore = todo!!.imagestore, imagestore1 = finalresponse, uploadstatus = getString(R.string.fileadded), frontandback = todo!!.frontandback, submitedfully = todo!!.submitedfully, document_name = finalresponse, uuid = todo!!.uuid,has_input = todo!!.has_input,account_number = todo!!.account_number)
//                    todoViewModel.updateTodo(todo!!)
//                    if(bottomSheetDialog != null)
//                    {
//                        if(bottomSheetDialog!!.isShowing)
//                        {
//                            secondimage=""
//                            secondimage=finalresponse
//                            mSessionManager.setdocuments_secondimage(finalresponse)
//                            Log.d("sdyhsgr34",finalresponse)
//
//                            nofilebackside!!.text = getString(R.string.fileadded)
//                            nofilebackside!!.textColor = Color.parseColor("#427fed")
//                        }
//                    }
//                }
//
//
//            }
//            else
//            {
//                loader.visibility = View.INVISIBLE
//                errormess(getString(R.string.connection_problem), getString(R.string.failed_upload_image))
//            }
//        }
//        else   if (apiName.equals(getString(R.string.finaldocumentupload)))
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
//                        val response = response_json_object.getString("response")
//                        val vehicleinfo = JSONObject(response)
//                        val vehicleinfoobj = vehicleinfo.getString("document")
//                        val vehicle_id = JSONObject(vehicleinfoobj)
//                        val id = vehicle_id.getString("id")
//                        if(id.equals(imagenames))
//                        {
//                            todo = documenttwodoRecord(id = todo!!.id, coloumnid = todo!!.coloumnid, coloumnname = todo!!.coloumnname, category = todo!!.category, is_req = todo!!.is_req, is_exp = todo!!.is_exp, notes = todo!!.notes, expirydate = todo!!.expirydate, imagestore = todo!!.imagestore, imagestore1 = finalresponse, uploadstatus = getString(R.string.fileadded), frontandback = todo!!.frontandback, submitedfully = "1", document_name = finalresponse, uuid = todo!!.uuid,has_input = todo!!.has_input,account_number = todo!!.account_number)
//                            todoViewModel.updateTodo(todo!!)
//                        }
//
//                        // todoViewModel.savedriverprofiledata(mContext,finalresponse)
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
//        }
//    }
//
//
//    fun documentthree()
//    {
//        val intent_otppage = Intent(mContext, turnlocationonandintenet::class.java)
//        intent_otppage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//        intent_otppage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        startActivity(intent_otppage)
//    }
//
//    fun documentcompleted()
//    {
//        val intent_otppage = Intent(mContext, mianscreenpage::class.java)
//        intent_otppage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//        intent_otppage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        startActivity(intent_otppage)
//    }
//
//
//
//    override fun onResume()
//    {
//        super.onResume()
//        getdriverdocument()
//
//        if(!EventBus.getDefault().isRegistered(this))
//        {
//            EventBus.getDefault().register(this);
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
//    override fun onDeleteClicked(todoRecord: documenttwodoRecord)
//    {
//        if(loader.visibility == View.VISIBLE)
//        {
//            errormess(getString(R.string.saving_image), getString(R.string.uploading_image))
//        }
//        else
//        {
//            todo = documenttwodoRecord(id = todoRecord.id, coloumnid = todoRecord.coloumnid, coloumnname = todoRecord.coloumnname, category = todoRecord.category, is_req = todoRecord.is_req, is_exp = todoRecord.is_exp,has_input = todoRecord.has_input,account_number =todoRecord.account_number,notes = todoRecord.notes, expirydate = todoRecord.expirydate, imagestore = todoRecord.imagestore, imagestore1 = todoRecord.imagestore1, uploadstatus = todoRecord.uploadstatus, frontandback = todoRecord.frontandback, submitedfully = todoRecord.submitedfully, document_name = todoRecord.document_name, uuid = todoRecord!!.uuid)
//            documentuploadd(todo!!.coloumnid, todo!!.coloumnname, todo!!.notes, todo!!.frontandback, todo!!.is_exp, todo!!.expirydate, todo!!.imagestore, todo!!.imagestore1, todo!!.category, todo!!.uuid,todo!!.has_input,todo!!.account_number)
//
//
//        }
//    }
//
//
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
//
//    private fun getcamerapermission()
//    {
//        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
//                ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
//        {
//            ActivityCompat.requestPermissions(mContext, arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), camerapermisssioncode)
//        }
//        else
//        {
//            cameraimage()
//        }
//    }
//
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
//
//
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
//                    todoViewModel.onImagecompress(mContext, actualImage, imagenames, loader,selectedImageUri)
//                }
//            }
//            else if (requestCode == PICK_CAMERA_IMAGE)
//            {
//                if(data != null)
//                {
//                    val extras = data?.getExtras()
//                    val imageBitmap = extras?.get("data") as Bitmap
//                    var myUri =  todoViewModel.getImageUri(mContext,imageBitmap)
//
//                    /*     var baos: ByteArrayOutputStream =  ByteArrayOutputStream()
//                         imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)*/
//                    todoViewModel.onImagecompresscamera(mContext, imageBitmap, imagenames, loader, myUri!!)
//                }
//            }
//            else if (requestCode == 1)
//            {
//                if(data != null)
//                {
//
//                }
//            }
//        }
//
//    }
//
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
//    fun cameraimage()
//    {
//        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        startActivityForResult(intent, PICK_CAMERA_IMAGE)
//
////        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
////        camera_FileUri = getOutputMediaFileUri(1)
////        intent.putExtra(MediaStore.EXTRA_OUTPUT, camera_FileUri)
////        startActivityForResult(intent, PICK_CAMERA_IMAGE)
//    }
//
////    fun getOutputMediaFileUri(type: Int): Uri? {
////        return Uri.fromFile(getOutputMediaFile(type))
////    }
//
////    private fun getOutputMediaFile(type: Int): File? {
////
////        // External sdcard location
////        val mediaStorageDir: File = File(
////                Environment
////                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), IMAGE_DIRECTORY_NAME)
////
////        // Create the storage directory if it does not exist
////        if (!mediaStorageDir.exists()) {
////            if (!mediaStorageDir.mkdirs()) {
////                return null
////            }
////        }
////
////        // Create a media file name
////        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss",
////                Locale.getDefault()).format(Date())
////        val mediaFile: File
////        mediaFile = if (type == 1) {
////            File(mediaStorageDir.path + File.separator
////                    + "IMG_" + timeStamp + ".jpg")
////        } else {
////            return null
////        }
////        return mediaFile
////    }
//
//    fun callgallerypart()
//    {
//        val intent= Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        startActivityForResult(intent, PICK_GALLERY_IMAGE)
//    }
//
//
//
//    override fun onSendClicked(todoItems: List<documenttwodoRecord>)
//    {
//        var mSessionManager: SessionManager? = null
//        var params = HashMap<String, String>()
//
//
//        var failed:Int = 0
//        for (k in 0 until todoItems.size)
//        {
//            if(todoItems[k].submitedfully.equals("0"))
//            {
//                failed = 1
//            }
//
//        }
//        if(failed == 0)
//        {
//
//            if(loader.visibility == View.VISIBLE)
//            {
//
//            }
//            else
//            {
//                loader.visibility = View.VISIBLE
//                mSessionManager = SessionManager(mContext)
//                todoViewModel.finalcalltohomepage(mContext, "DRIVER_DOC_SUBMITTED")
//
//            }
//
//
//        }
//        else
//        {
//            AppUtils.commonerrorsheet(mContext, getString(R.string.failed), getString(R.string.fillallfile))
//        }
//    }
//
//    fun removebottomsheet()
//    {
//        val bottomSheetDialog =  BottomSheetDialog(this)
//        val view = getLayoutInflater().inflate(R.layout.includechooseselection, null);
//        bottomSheetDialog.setContentView(view)
//
//        val title = view.findViewById(R.id.title) as TextView
//        val subcontent = view.findViewById(R.id.subcontent) as TextView
//
//
//        title.setText(getString(R.string.document_s))
//        subcontent.setText(getString(R.string.add_vehicle_document_to_cabily))
//
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
//    fun expnd(dialogInterface: DialogInterface)
//    {
//        var d :BottomSheetDialog = dialogInterface as BottomSheetDialog
//        val bottomsheet = d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
//        val bottomsheetbeh = BottomSheetBehavior.from(bottomsheet!!)
//        bottomsheetbeh.state = BottomSheetBehavior.STATE_EXPANDED
//        bottomsheetbeh.peekHeight = bottomsheet!!.height
//    }
//    fun documentuploadd(id: String, name: String, notes: String, frontorbackimage: String, isexp: String, expirydate: String, imagestore: String, imagestore1: String, category: String, uuid: String,has_input: String,account_number:String)
//    {
//        var params = HashMap<String, String>()
//        val type = JSONObject()
//        type.put("uuid", uuid)
//        type.put("id", id)
//        type.put("name", name)
//
//        firstimage=imagestore
//        secondimage=imagestore1
//        Log.d("shjrher",secondimage)
//        if (secondimage.startsWith("{")){
//            Log.d("dafsg5yyff",secondimage)
//            secondimage = mSessionManager.getdocuments_secondimage()
//            Log.d("dsfdgfdg44",secondimage)
//
//        }
//
//
//
//
//
//
//        bottomSheetDialog=  BottomSheetDialog(this)
//        val view = getLayoutInflater().inflate(R.layout.documentsupload, null);
//        bottomSheetDialog!!.setContentView(view)
//
//        bottomSheetDialog!!.setOnShowListener(DialogInterface.OnShowListener { dialogInterface: DialogInterface? ->
//            if (dialogInterface != null) {
//                expnd(dialogInterface)
//            }
//        })
//
//        val title = view.findViewById(R.id.title) as TextView
//        val subcontent = view.findViewById(R.id.subcontent) as TextView
//
//        imagenames = id
//        title.setText(name)
//        subcontent.setText(notes)
//
//        val ok = view.findViewById(R.id.ok) as LinearLayout
//        val close = view.findViewById(R.id.close) as LinearLayout
//
//        val fronside = view.findViewById(R.id.fronside) as LinearLayout
//        val backside = view.findViewById(R.id.backside) as LinearLayout
//
//        val dateall = view.findViewById(R.id.dateall) as LinearLayout
//        val dateall_one = view.findViewById(R.id.dateall_one) as LinearLayout
//
//
//        val choosefilefrontside = view.findViewById(R.id.choosefilefrontside) as TextView
//         nofilefronside = view.findViewById(R.id.nofilefronside) as TextView
//
//        val choosefilebackside = view.findViewById(R.id.choosefilebackside) as TextView
//         nofilebackside = view.findViewById(R.id.nofilebackside) as TextView
//        accountnumber = view.findViewById(R.id.accountnumber) as EditText
//
//         expirydateclick= view.findViewById(R.id.expirydateclick) as Button
//
//        expirydateclick!!.text = expirydate
//        maccountnumber=accountnumber!!.text.toString()
//        Log.d("mcheckimhs",accountnumber!!.text.toString())
//        Log.d("mcheckim355hs", maccountnumber!!)
//
//        if(isexp.equals("1"))
//        {
//            mContext.runOnUiThread {
//                dateall.setVisibility(View.VISIBLE)
//            }
//        }
//        else
//        {
//            dateall.visibility = View.GONE
//        }
//
//        if(has_input.equals("1"))
//        {
//            mContext.runOnUiThread {
//                dateall_one.setVisibility(View.VISIBLE)
//            }
//        }
//        else
//        {
//            dateall_one.visibility = View.GONE
//        }
//
//
//        if(!firstimage.equals(""))
//        {
//            nofilefronside!!.text=getString(R.string.fileadded)
//            nofilefronside!!.textColor = Color.parseColor("#427fed")
//        }
//        if(!secondimage.equals(""))
//        {
//            nofilebackside!!.text=getString(R.string.fileadded)
//            nofilebackside!!.textColor = Color.parseColor("#427fed")
//        }
//
//
//
//        if(frontorbackimage.equals("3"))
//        {
//            fronside.visibility = View.VISIBLE
//            backside.visibility = View.VISIBLE
//        }
//        else if(frontorbackimage.equals("1"))
//        {
//            fronside.visibility = View.VISIBLE
//            backside.visibility = View.GONE
//        }
//        else if(frontorbackimage.equals("2"))
//        {
//            fronside.visibility = View.GONE
//            backside.visibility = View.VISIBLE
//        }
//        else
//        {
//            fronside.visibility = View.VISIBLE
//            backside.visibility = View.GONE
//        }
//
//        expirydateclick!!.setOnClickListener {
//            if(loader.visibility == View.VISIBLE)
//            {
//                errormess(getString(R.string.saving_image), getString(R.string.pleae_wait_image))
//            }
//            else
//            {
//                 todoViewModel.returndob(mContext)
//            }
//        }
//
//        nofilefronside!!.setOnClickListener {
//            if(!firstimage.equals(""))
//            {
//                val intent_otppage = Intent(mContext, imagepreviewpage::class.java)
//                intent_otppage.putExtra("imagepath", getString(R.string.amazonbucketurl) + firstimage)
//                startActivity(intent_otppage)
//            }
//        }
//
//        nofilebackside!!.setOnClickListener {
//            if(!secondimage.equals(""))
//            {
//                val intent_otppage = Intent(mContext, imagepreviewpage::class.java)
//                intent_otppage.putExtra("imagepath", getString(R.string.amazonbucketurl) + secondimage)
//                startActivity(intent_otppage)
//            }
//        }
//
//
//        choosefilefrontside.setOnClickListener {
//            if(loader.visibility == View.VISIBLE)
//            {
//                errormess(getString(R.string.saving_image), getString(R.string.uploading_image))
//            }
//            else
//            {
//                frontorbacksideupload="1"
//                removebottomsheet()
//            }
//
//        }
//        choosefilebackside.setOnClickListener {
//            if(loader.visibility == View.VISIBLE)
//            {
//                errormess(getString(R.string.saving_image), getString(R.string.uploading_image))
//            }
//            else
//            {
//                frontorbacksideupload="2"
//                removebottomsheet()
//            }
//
//        }
//
//        ok.setOnClickListener {
//            if(loader.visibility == View.VISIBLE) {
//                errormess(getString(R.string.saving_image), getString(R.string.loadings))
//                loader.visibility = View.INVISIBLE
//            }else {
//                if (frontorbackimage.equals("3")) {
//                    if (firstimage.equals("") || secondimage.equals("")) {
//                        errormess(getString(R.string.choosefile), getString(R.string.chooseimagemissing))
//                    } else {
//                        if (isexp.equals("1")) {
//                            if (expirydateclick!!.text.equals("")) {
//                                errormess(getString(R.string.expirydate), getString(R.string.selectexpirydate))
//                            } else {
//
//                                if (has_input.equals("1")) {
//                                    if (accountnumber!!.text.toString().equals("")) {
//                                        errormess(getString(R.string.account_number_document), getString(R.string.account_number))
//                                    } else {
//                                        loader.visibility = View.VISIBLE
//                                        type.put("expiry_date", returnconverdate(expirydateclick!!.text.toString()))
//                                        type.put("front", firstimage)
//                                        type.put("back", secondimage)
//                                        type.put("input", accountnumber!!.text.toString())
//
//                                        var op: String = type.toString()
//                                        params.put("document", op)
//
//                                        todoViewModel.updatedocument(mContext, params, category)
//                                        bottomSheetDialog!!.dismiss()
//                                    }
//                                }else{
//                                    loader.visibility = View.VISIBLE
//                                    type.put("expiry_date", returnconverdate(expirydateclick!!.text.toString()))
//                                    type.put("front", firstimage)
//                                    type.put("back", secondimage)
//                                    type.put("input", accountnumber!!.text.toString())
//
//                                    var op: String = type.toString()
//                                    params.put("document", op)
//
//                                    todoViewModel.updatedocument(mContext, params, category)
//                                    bottomSheetDialog!!.dismiss()
//                                }
//
//
//                            }
//                        } else {
//                            loader.visibility = View.VISIBLE
//                            type.put("expiry_date", returnconverdate(expirydateclick!!.text.toString()))
//                            type.put("front", firstimage)
//                            type.put("back", secondimage)
//                            type.put("input", accountnumber!!.text.toString())
//
//                            var op: String = type.toString()
//                            params.put("document", op)
//
//                            todoViewModel.updatedocument(mContext, params, category)
//                            bottomSheetDialog!!.dismiss()
//                        }
//                    }
//
//                } else if (frontorbackimage.equals("1")) {
//
//
////                    if (has_input.equals("1")) {
////                        if (accountnumber!!.text.toString().equals("")) {
////                            errormess(getString(R.string.expirydate), getString(R.string.selectexpirydate))
////                        } else {
////                            loader.visibility = View.VISIBLE
////                            type.put("expiry_date", returnconverdate(expirydateclick!!.text.toString()))
////                            type.put("front", firstimage)
////                            type.put("back", secondimage)
////                            type.put("input", accountnumber!!.text.toString())
////
////                            var op: String = type.toString()
////                            params.put("document", op)
////
////                            todoViewModel.updatedocument(mContext, params, category)
////                            bottomSheetDialog!!.dismiss()
////                        }
////                    } else {
////                        loader.visibility = View.VISIBLE
////                        type.put("expiry_date", returnconverdate(expirydateclick!!.text.toString()))
////                        type.put("front", firstimage)
////                        type.put("back", secondimage)
////                        type.put("input", accountnumber!!.text.toString())
////
////                        var op: String = type.toString()
////                        params.put("document", op)
////
////                        todoViewModel.updatedocument(mContext, params, category)
////                        bottomSheetDialog!!.dismiss()
////                    }
//
//
//
//                    if (firstimage.equals("")) {
//                        errormess(getString(R.string.choosefile), getString(R.string.chooseimagemissing))
//                    } else {
//                        if (isexp.equals("1")) {
//                            if (expirydateclick!!.text.equals("")) {
//                                errormess(getString(R.string.expirydate), getString(R.string.selectexpirydate))
//                            } else {
//                                loader.visibility = View.VISIBLE
//                                type.put("expiry_date", returnconverdate(expirydateclick!!.text.toString()))
//                                type.put("front", firstimage)
//                                type.put("back", secondimage)
//                                type.put("input", accountnumber!!.text.toString())
//
//                                var op: String = type.toString()
//                                params.put("document", op)
//
//                                todoViewModel.updatedocument(mContext, params, category)
//                                bottomSheetDialog!!.dismiss()
//                            }
//                        } else {
//                            loader.visibility = View.VISIBLE
//                            type.put("expiry_date", returnconverdate(expirydateclick!!.text.toString()))
//                            type.put("front", firstimage)
//                            type.put("back", secondimage)
//                            type.put("input", accountnumber!!.text.toString())
//
//                            var op: String = type.toString()
//                            params.put("document", op)
//
//                            todoViewModel.updatedocument(mContext, params, category)
//                            bottomSheetDialog!!.dismiss()
//                        }
//                    }
//
//                } else if (frontorbackimage.equals("2")) {
//                    if (has_input.equals("1")) {
//                        if (accountnumber!!.text.equals("")) {
//                            errormess(getString(R.string.expirydate), getString(R.string.selectexpirydate))
//                        } else {
//                            loader.visibility = View.VISIBLE
//                            type.put("expiry_date", returnconverdate(expirydateclick!!.text.toString()))
//                            type.put("front", firstimage)
//                            type.put("back", secondimage)
//                            type.put("input", accountnumber!!.text.toString())
//
//                            var op: String = type.toString()
//                            params.put("document", op)
//
//                            todoViewModel.updatedocument(mContext, params, category)
//                            bottomSheetDialog!!.dismiss()
//                        }
//                    } else {
//                        loader.visibility = View.VISIBLE
//                        type.put("expiry_date", returnconverdate(expirydateclick!!.text.toString()))
//                        type.put("front", firstimage)
//                        type.put("back", secondimage)
//                        type.put("input", accountnumber!!.text.toString())
//
//                        var op: String = type.toString()
//                        params.put("document", op)
//
//                        todoViewModel.updatedocument(mContext, params, category)
//                        bottomSheetDialog!!.dismiss()
//                    }
//
//
//
//                    if (secondimage.equals("")) {
//                        errormess(getString(R.string.choosefile), getString(R.string.chooseimagemissing))
//                    } else {
//                        if (isexp.equals("1")) {
//                            if (expirydateclick!!.text.equals("")) {
//                                errormess(getString(R.string.expirydate), getString(R.string.selectexpirydate))
//                            } else {
//
//                                loader.visibility = View.VISIBLE
//                                type.put("expiry_date", returnconverdate(expirydateclick!!.text.toString()))
//                                type.put("front", firstimage)
//                                type.put("back", secondimage)
//                                type.put("input", accountnumber!!.text.toString())
//
//                                var op: String = type.toString()
//                                params.put("document", op)
//
//                                todoViewModel.updatedocument(mContext, params, category)
//                                bottomSheetDialog!!.dismiss()
//                            }
//                        } else {
//                            loader.visibility = View.VISIBLE
//                            type.put("expiry_date", returnconverdate(expirydateclick!!.text.toString()))
//                            type.put("front", firstimage)
//                            type.put("back", secondimage)
//                            type.put("input", accountnumber!!.text.toString())
//
//                            var op: String = type.toString()
//                            params.put("document", op)
//                            todoViewModel.updatedocument(mContext, params, category)
//
//                            bottomSheetDialog!!.dismiss()
//                        }
//                    }
//
//                } else {
//                    if (has_input.equals("1")) {
//                        if (accountnumber!!.text.equals("")) {
//                            errormess(getString(R.string.expirydate), getString(R.string.selectexpirydate))
//                        } else {
//                            loader.visibility = View.VISIBLE
//                            type.put("expiry_date", returnconverdate(expirydateclick!!.text.toString()))
//                            type.put("front", firstimage)
//                            type.put("back", secondimage)
//                            type.put("input", accountnumber!!.text.toString())
//
//                            var op: String = type.toString()
//                            params.put("document", op)
//
//                            todoViewModel.updatedocument(mContext, params, category)
//                            bottomSheetDialog!!.dismiss()
//                        }
//                    } else {
//                        loader.visibility = View.VISIBLE
//                        type.put("expiry_date", returnconverdate(expirydateclick!!.text.toString()))
//                        type.put("front", firstimage)
//                        type.put("back", secondimage)
//                        type.put("input", accountnumber!!.text.toString())
//
//                        var op: String = type.toString()
//                        params.put("document", op)
//
//                        todoViewModel.updatedocument(mContext, params, category)
//                        bottomSheetDialog!!.dismiss()
//                    }
//
//                    if (firstimage.equals("")) {
//                        errormess(getString(R.string.choosefile), getString(R.string.chooseimagemissing))
//                    } else {
//                        if (isexp.equals("1")) {
//                            if (expirydateclick!!.text.equals("")) {
//                                errormess(getString(R.string.expirydate), getString(R.string.selectexpirydate))
//                            } else {
//                                loader.visibility = View.VISIBLE
//                                type.put("expiry_date", returnconverdate(expirydateclick!!.text.toString()))
//                                type.put("front", firstimage)
//                                type.put("back", secondimage)
//                                type.put("input", accountnumber!!.text.toString())
//
//                                var op: String = type.toString()
//                                params.put("document", op)
//
//                                todoViewModel.updatedocument(mContext, params, category)
//                                bottomSheetDialog!!.dismiss()
//                            }
//                        } else {
//                            loader.visibility = View.VISIBLE
//                            type.put("expiry_date", returnconverdate(expirydateclick!!.text.toString()))
//                            type.put("front", firstimage)
//                            type.put("back", secondimage)
//                            type.put("input", accountnumber!!.text.toString())
//
//                            var op: String = type.toString()
//                            params.put("document", op)
//
//                            todoViewModel.updatedocument(mContext, params, category)
//                            bottomSheetDialog!!.dismiss()
//                        }
//                    }
//
//                    Log.d("mhassup",has_input)
//
//
//
//
//
//
//                }
//            }
//        }
//
//        close.setOnClickListener {
//
//            bottomSheetDialog!!.dismiss()
//
//        }
//
//        bottomSheetDialog!!.show()
//    }
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
//
//    fun successdialog(heading: String, content: String)
//    {
////        val bottomSheetDialog =  BottomSheetDialog(this)
////        val view = getLayoutInflater().inflate(R.layout.successdialogadmin, null);
////        bottomSheetDialog.setContentView(view)
////        bottomSheetDialog.setCancelable(false)
////
////        val title = view.findViewById(R.id.title) as TextView
////        val subcontent = view.findViewById(R.id.subcontent) as TextView
////
////        title.setText(heading)
////        subcontent.setText(content)
////
//////        val backtologinlayout = view.findViewById(R.id.backtologinlayout) as LinearLayout
//////        backtologinlayout.visibility = View.GONE
////
////        val done = view.findViewById(R.id.done) as LinearLayout
////
////        done.setOnClickListener {
////            bottomSheetDialog.dismiss()
////            documentthree()
////        }
////
////        bottomSheetDialog.show()
//    }
//
//
//    fun returnconverdate(passdate: String): String?
//    {
//        var converteddate:String = ""
//        if(passdate.equals("") || passdate.length < 3)
//        {
//            converteddate = ""
//        }
//        else
//        {
//            var spf = SimpleDateFormat("dd/MM/yyyy")
//            val newDate: Date = spf.parse(passdate)
//            spf = SimpleDateFormat("yyyy-MM-dd")
//            converteddate = spf.format(newDate)
//
//        }
//        return converteddate
//    }
//}