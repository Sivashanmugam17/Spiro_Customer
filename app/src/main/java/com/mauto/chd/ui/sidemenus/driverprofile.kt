package com.mauto.chd.ui.sidemenus

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide
import com.mauto.chd.backgroundservices.commonapifetchservice
import com.mauto.chd.backgroundservices.logoutcallapi
import com.mauto.chd.event_bus_connection.IntentServiceResult
import com.mauto.chd.Locales
import com.mauto.chd.Modal.DriverProofModel
import com.mauto.chd.SessionManagerPackage.SessionManager
import com.mauto.chd.clickableInterface.rideCustomOnClickListener
import com.mauto.chd.data.LanguageDb
import com.mauto.chd.ui.registeration.LocaleAwareCompatActivity
import com.mauto.chd.ui.registeration.splashpage
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mauto.chd.BuildConfig
import com.mauto.chd.R

import io.realm.Realm
import kotlinx.android.synthetic.main.activity_support.backbutton
import kotlinx.android.synthetic.main.driverprofile.*
import kotlinx.android.synthetic.main.driverprofile.appversion
//import kotlinx.android.synthetic.main.registerpagefour.*
import mauto_customer.ui.adapter.Driverproofadapter
//import kotlinx.android.synthetic.main.requestpagedfa.*
import mauto_customer.ui.driverprofilemodel
import mauto_customer.ui.MainscreenCustomers
import mauto_customer.ui.sidemenus.Changepassword
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject
import java.util.*

class driverprofile : LocaleAwareCompatActivity() {
    private lateinit var mContext: Activity
    internal lateinit var mSessionManager: SessionManager
    private var transactionHistoryModel:ArrayList<DriverProofModel>?=null

    var customerphno: String? = ""
    var selectedtype: String? = ""
    var selectedlanage: String? = "fr"
    lateinit var mHelpers: LanguageDb
    private lateinit var viewModel: driverprofilemodel
    private val mLocationPermissionCode = 999
    private val PICK_GALLERY_IMAGE = 2
    private val PICK_CAMERA_IMAGE = 1
    private val camerapermisssioncode = 888
    lateinit var triplist:RecyclerView
    private lateinit var tripadapater: Driverproofadapter
    var app_version:String = ""

    override fun onPause() {
        super.onPause()
        EventBus.getDefault().unregister(this)
    }

    override fun onResume() {
        super.onResume()
        getFaqruser()
        if (mSessionManager.getseletedlagname().equals("fr")){
            text_spinner.setText("English ")
        }else{
            text_spinner.setText("French ")
        }

        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*if(savedInstanceState==null){
            val intent = Intent(this@driverprofile, splashpage::class.java)
            startActivity(intent)
        }*/
        setContentView(R.layout.driverprofile)
        mContext = this@driverprofile
        mSessionManager = SessionManager(mContext)
        mHelpers = LanguageDb(applicationContext)
        viewModel = ViewModelProviders.of(this).get(driverprofilemodel::class.java)
        triplist = findViewById(R.id.transactionlistnew)
        getFaqruser()
        lg_set()
//        lan_select()
        driverpic()
        app_version= BuildConfig.VERSION_NAME
        appversion.setText("V "+app_version)

        if (mSessionManager.getis_electric().equals("true")) {
            vehicle_lay?.visibility = View.GONE

        } else {
            vehicle_lay?.visibility = View.GONE


        }

//        vehicle_number.setText(mSessionManager.getUserDetails()[mSessionManager.getVehicleno()]!!)
        pick_image.setOnClickListener {
//                bottomSheetLayout_setting.toggle()


        }


        backbutton?.setOnClickListener {
            val intent2 = Intent(mContext, MainscreenCustomers()::class.java)
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            mContext.startActivity(intent2)
        }
        language_click?.setOnClickListener {
            chooselanguage()
        }
//        btn_done_setting.setOnClickListener {
//            bottomSheetLayout_setting.toggle()
//        }
        changepassword_lay.setOnClickListener {
            val intent2 = Intent(mContext, Changepassword()::class.java)
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            mContext.startActivity(intent2)
        }
        logout_lay.setOnClickListener {
            exitappalert()
        }




    }
    fun exitappalert() {
        val builder = AlertDialog.Builder(mContext)
        builder.setTitle(getString(R.string.confirm_cancel))
        builder.setMessage(getString(R.string.wanttoexit))
        builder.setPositiveButton(getString(R.string.yesia)) { dialog, which ->
            mSessionManager = SessionManager(applicationContext!!)
            mSessionManager.clearalldata()
            backtologin(applicationContext)
            logout(mContext)

        }
        builder.setNegativeButton(getString(R.string.closepage)) { dialog, which ->
        }
        builder.show()
    }
    fun logout(mcontext:Context) {
        val serviceClass = logoutcallapi::class.java
        val intent = Intent(mcontext, serviceClass)
        intent.putExtra(mcontext.getString(R.string.intent_putextra_api_key), mcontext.getString(R.string.logoutcallapi))
        mcontext.startService(intent)
    }

    fun backtologin(mContext: Context) {
        val realm: Realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        realm.deleteAll()
        realm.commitTransaction()
        val intent2 = Intent(mContext, splashpage::class.java)
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        mContext.startActivity(intent2)
    }


    fun uploadimagefromgallery_setting(view: View) {

        getstoragepermission()
//        bottomSheetLayout_setting.toggle()

    }
    fun uploadimagefromcamera_setting(view: View) {
        getcamerapermission()
//        bottomSheetLayout_setting.toggle()

    }
    private fun getcamerapermission() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mContext, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA),
                    camerapermisssioncode)
        } else {
            cameraimage()
        }
    }

    fun cameraimage() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, PICK_CAMERA_IMAGE)
    }


    private fun getstoragepermission() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mContext, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE),
                    mLocationPermissionCode)
        } else {
            callgallerypart()
        }
    }
    fun callgallerypart() {

            val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_GALLERY_IMAGE)

    }



//    private fun lan_select() {
//        var vehicle = ArrayList<String>()
////             vehicle.addAll(VehicleDetailsViewModel.myCategorytype)
//        vehicle.add("English")
//        vehicle.add("French ")
////        vehicle.add("Auto")
//        if (lan_spinner != null) {
//            val adapterd = vehicletypeCustomAdapter(this, vehicle)
//            lan_spinner?.adapter = adapterd
//            lan_spinner?.onItemSelectedListener = object :
//                    AdapterView.OnItemSelectedListener {
//                override fun onItemSelected(parent: AdapterView<*>,
//                                            view: View?, position: Int, id: Long) {
//
//                    selectedtype = position.toString()
//
//
//                    if (selectedtype!!.equals("1")) {
//                        val lang = "en" // your language code
//                        updateLocale(Locales.English)
//                        resartapp()
//                    }
//                    if (selectedtype!!.equals("1")) {
//                        val lang = "fa" // your language code
//                        updateLocale(Locales.French)
//                        resartapp()
//
//
//                    }
//
//                }
//
//                override fun onNothingSelected(parent: AdapterView<*>) {
//                    // write code to perform some action
//                }
//            }
//        }
//    }

    override fun onBackPressed() {
        val intent2 = Intent(mContext, MainscreenCustomers()::class.java)
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        mContext.startActivity(intent2)

    }
    fun chooselanguage()
    {
        var languagedialog =  BottomSheetDialog(this)
        val view = getLayoutInflater().inflate(R.layout.chooselanguagesheet, null);
        languagedialog!!.setContentView(view)
        languagedialog!!.setCancelable(true)
        val english_lay = view.findViewById(R.id.english_lay) as LinearLayout
        val french_lay = view.findViewById(R.id.french_lay) as LinearLayout
        val cloepage = view.findViewById(R.id.cloepageoflanguage) as LinearLayout

        val englishchoosen = view.findViewById(R.id.englishchoosen) as TextView
        val tamilchoosen = view.findViewById(R.id.tamilchoosen) as TextView
        val urduchoosen = view.findViewById(R.id.urduchoosen) as TextView
//        englishchoosen.setOnClickListener {
//            languagedialog!!.dismiss()
//            updateLocale(Locales.English)
//        }

        french_lay.setOnClickListener {
            languagedialog!!.dismiss()
            updateLocale(Locales.French)
            selectedlanage="fr"
            mSessionManager.setseletedlagname(selectedlanage!!)
            resartapp()

        }


        english_lay.setOnClickListener {
            languagedialog!!.dismiss()
            updateLocale(Locales.English)
            selectedlanage="en"
            mSessionManager.setseletedlagname(selectedlanage!!)
            resartapp()

        }

        cloepage.setOnClickListener {
            languagedialog!!.dismiss()
        }
        languagedialog!!.show()
    }


    fun resartapp() {
        val mainpage = Intent(mContext, splashpage::class.java)
        mainpage.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(mainpage)
        finish()
    }

    fun lg_set(){
//        email_id.setText(getkey("emailid"))
//        address_id.setText(getkey("address"))
    }
    private fun getkey(key: String): String? {
        return mHelpers.getvalueforkey(key)
    }


    fun driverpic() {
        var driverpath = mSessionManager.getdriver_profile_image()

        Glide.with(mContext)
                .load(driverpath)
                .into(profile_profileimg)

    }


    fun getFaqruser() {
        progress_lay_profile.visibility=View.VISIBLE
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.profile_driver))
        mContext.startService(intent)
    }

    //calling customer

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun doThis(intentServiceResult: IntentServiceResult) {
        var apiName: String = intentServiceResult.apiName
        var finalresponse: String = intentServiceResult.resultValue
        if (apiName.equals(getString(R.string.profile_driver))) {
            if (finalresponse == "failed") {
                progress_lay_profile.visibility=View.GONE

//                toast(getString(R.string.api_failure_toast_label))
            } else {


                val response_json_object = JSONObject(finalresponse)
                Log.d("sdfbhsbfd", response_json_object.toString())
                val status = response_json_object.getString("status")
                if (status.equals("1")) {
                    transactionHistoryModel = ArrayList()


                    val response = response_json_object.getJSONObject("response")
                    val driver_info = response.getJSONObject("driver_info")
                    var name = driver_info.getString("name")
                    var phone_number = driver_info.getString("phone_number")
                    var dial_code = driver_info.getString("dial_code")
                    var email = driver_info.getString("email")
                    var ratting = driver_info.getString("ratting")
                    var address = driver_info.getJSONObject("address")
                    var addresss = address.getString("address")
                    var state = address.getString("state")
                    var city = address.getString("city")
                    var country = address.getString("country")
                    var zipcode = address.getString("zipcode")
                    var gender = driver_info.getString("gender")

                    contact_number.text = dial_code+" "+phone_number
                    emailid_text.text = email
                    name_text.text = name
//                    emailid_text.text = email
                    mob_num.text = dial_code+" "+phone_number
                    address_text.text = addresss+","+state+","+city+","+country+","+zipcode
                    rating.text = ratting
                    Gender_text.text = gender
                    Address_text.text = addresss+","+state+","+city+","+country+","+zipcode

                    val documentsarray2 = driver_info.getJSONArray("documents")
                    if (documentsarray2.length() > 0) {
                        for (g in 0 until documentsarray2.length()) {
                            val response_object_faq2 = documentsarray2.getJSONObject(g)

                            var vehicle_id = response_object_faq2.getString("vehicle_id")
                            var type = response_object_faq2.getString("type")
                            var proofname = response_object_faq2.getString("name")

                            if (type.equals("vehicle")) {
                                var front = response_object_faq2.getString("front")
                                var back = response_object_faq2.getString("back")
                                var documentimagepathfront = getString(R.string.amazonbucketurl) + front
                                var documentimagepathback = getString(R.string.amazonbucketurl) + back

                                Glide.with(mContext)
                                        .load(documentimagepathfront)
                                        .into(id_proof_imageview)
//                                Glide.with(mContext)
//                                        .load(documentimagepathback)
//                                        .into(vehicle_document1)


                            } else {

                                var front1 = response_object_faq2.getString("front_url")
                                var backss = response_object_faq2.getString("back_url")
                                var voterimagepathfront = front1
                                var voterimagepathback = getString(R.string.amazonbucketurl) + backss
                                Log.d("dfahfase3dsf", voterimagepathfront )
                                Glide.with(mContext)
                                        .load(voterimagepathfront)
                                        .into(id_proof_imageview)
//                                Glide.with(mContext)
//                                        .load(voterimagepathback)
//                                        .into(voterdoc1)
                                transactionHistoryModel?.add(DriverProofModel(front1,backss,proofname));

                            }


                        }
                        progress_lay_profile.visibility=View.GONE

                        initrecyclerviews(transactionHistoryModel!!)

                    }else{
                        progress_lay_profile.visibility=View.GONE

                    }

                }


            }
        }
    }

    private fun initrecyclerviews(transactionHistoryModel: ArrayList<DriverProofModel>) {
        triplist.layoutManager = LinearLayoutManager(mContext)
        tripadapater = Driverproofadapter(mContext!!,transactionHistoryModel, object : rideCustomOnClickListener {
            override fun onItemClickListener(view: View, position: Int,rideid:String)
            {
//                val clickedName = transactionHistoryModel.get(position).name
            }
        })
        triplist.adapter = tripadapater
        triplist.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

            }
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }


    //common error notification page


}