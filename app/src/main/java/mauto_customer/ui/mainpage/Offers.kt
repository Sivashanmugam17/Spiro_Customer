package mauto_customer.ui.mainpage

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.text.Html
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mauto.chd.BuildConfig
import com.mauto.chd.Locales
import com.mauto.chd.Modal.DriverProofModel

import com.mauto.chd.R
import com.mauto.chd.SessionManagerPackage.SessionManager
import com.mauto.chd.backgroundservices.commonapifetchservice
import com.mauto.chd.backgroundservices.logoutcallapi
import com.mauto.chd.clickableInterface.rideCustomOnClickListener
import com.mauto.chd.data.LanguageDb
import com.mauto.chd.event_bus_connection.IntentServiceResult
import com.mauto.chd.ui.registeration.LocaleAwareCompatActivity
import com.mauto.chd.ui.registeration.splashpage
import com.mauto.chd.ui.sidemenus.PaymentOtherTypePage
import com.mauto.chd.ui.sidemenus.Voucher_purchesd
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_support.*
import kotlinx.android.synthetic.main.activity_wallet.*
import kotlinx.android.synthetic.main.driverprofile.*
import kotlinx.android.synthetic.main.driverprofile.appversion
import kotlinx.android.synthetic.main.driverprofile.changepassword_lay
import kotlinx.android.synthetic.main.driverprofile.language_click
import kotlinx.android.synthetic.main.driverprofile.logout_lay
import kotlinx.android.synthetic.main.driverprofile.pick_image
import kotlinx.android.synthetic.main.driverprofile.profile_profileimg
import kotlinx.android.synthetic.main.driverprofile.progress_lay_profile
import kotlinx.android.synthetic.main.driverprofile.text_spinner
import kotlinx.android.synthetic.main.driverprofile.vehicle_lay
import kotlinx.android.synthetic.main.offers.*
import kotlinx.android.synthetic.main.offers.backbutton12
import kotlinx.android.synthetic.main.offers.id_proof_imageview1
import kotlinx.android.synthetic.main.offers.progress_lay_profile1
import kotlinx.android.synthetic.main.offers.transactionlistnew23
import kotlinx.android.synthetic.main.offers.transactionlistnew233
import kotlinx.android.synthetic.main.offers.vouchers23
import kotlinx.android.synthetic.main.offers.vouchers233
import kotlinx.android.synthetic.main.vouchers.*
import mauto_customer.ui.MainscreenCustomers
import mauto_customer.ui.OfferProoModel
import mauto_customer.ui.VoucherModul
import mauto_customer.ui.adapter.OffersviewAdapter
import mauto_customer.ui.adapter.VoucherAdapter
import mauto_customer.ui.driverprofilemodel
import mauto_customer.ui.sidemenus.Changepassword
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.toast
import org.json.JSONObject
import java.util.ArrayList

class Offers : LocaleAwareCompatActivity() {
    private lateinit var mContext: Activity
    internal lateinit var mSessionManager: SessionManager
    private var transactionHistoryModel: ArrayList<OfferProoModel>?=null
    private var voucherHistoryModel: ArrayList<VoucherModul>?=null

    var customerphno: String? = ""
    var selectedtype: String? = ""
    var voucher_purchase_value: String? = ""
    var selectedlanage: String? = "fr"
    lateinit var mHelpers: LanguageDb
    private lateinit var viewModel: driverprofilemodel
    private val mLocationPermissionCode = 999
    private val PICK_GALLERY_IMAGE = 2
    var currencycode:String = ""
    private val RESULT_PAYMENT_METHOD: Int = 1001
    private val PICK_CAMERA_IMAGE = 1
    private val camerapermisssioncode = 888
    lateinit var triplist: RecyclerView
    lateinit var voucherlist: RecyclerView
    private lateinit var tripadapater: OffersviewAdapter
    private lateinit var voucherapater: VoucherAdapter
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
        setContentView(R.layout.offers)
        mContext = this@Offers
        mSessionManager = SessionManager(mContext)
        mHelpers = LanguageDb(applicationContext)
        viewModel = ViewModelProviders.of(this).get(driverprofilemodel::class.java)
        triplist = findViewById(R.id.transactionlistnew1)
        voucherlist = findViewById(R.id.vouchers)
        getFaqruser()
        lg_set()
//        lan_select()
        driverpic()

//        getwalletbalance()
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


        backbutton12?.setOnClickListener {
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
        backbutton13.setOnClickListener {
            val intent = Intent(mContext, Voucher_purchesd()::class.java)
            intent.putExtra("currencycode", currencycode)
            intent.putExtra("voucher_purchase_value",voucher_purchase_value)
            mContext.startActivity(intent)
        }
        changepassword_lay.setOnClickListener {
            val intent2 = Intent(mContext, Changepassword()::class.java)
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            mContext.startActivity(intent2)
        }
        logout_lay.setOnClickListener {
            exitappalert()
        }


        transactionlistnew233.setOnClickListener {
            voucherlist.visibility= View.GONE
            backbutton13.visibility= View.GONE
            triplist.visibility= View.VISIBLE
            transactionlistnew233.visibility= View.GONE
            vouchers233.visibility= View.GONE
            vouchers23.visibility= View.VISIBLE
            transactionlistnew23.visibility= View.VISIBLE
//            toast("offers")
        }
        vouchers23.setOnClickListener {
            triplist.visibility= View.GONE
            vouchers23.visibility= View.GONE
            transactionlistnew23.visibility= View.GONE
            voucherlist.visibility= View.VISIBLE
            transactionlistnew233.visibility= View.VISIBLE
            backbutton13.visibility= View.VISIBLE
            vouchers233.visibility= View.VISIBLE
//            toast("voucher")
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
    fun logout(mcontext: Context) {
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

        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
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
            resartapp()
            selectedlanage="fr"
            mSessionManager.setseletedlagname(selectedlanage!!)


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
        progress_lay_profile1.visibility= View.VISIBLE
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.offer_copen))
        mContext.startService(intent)
    }


    fun offerDailog(id: String?,credit_amount:String?,do_purchase: String?,
                    description:String?,offer_image:String?,terms:String?,code:String?
    ,name:String?,purchase_amount:String?,currencycode:String?,validity:String?){

        val dialog = Dialog(mContext, android.R.style. Theme_Light_NoTitleBar_Fullscreen )
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.offer_descryption_dilog)
        dialog.window!!.setGravity(Gravity.CENTER)
        val displayMetrics = DisplayMetrics()
        var description1 = dialog.findViewById<TextView>(R.id.description1)
        var station_images1=dialog.findViewById<ImageView>(R.id.station_images1)
        var getdirecation_lay =dialog.findViewById<LinearLayout>(R.id.getdirecation_lay)
        var close =dialog.findViewById<LinearLayout>(R.id.close1offer)
        var amoun = dialog.findViewById<TextView>(R.id.amount1)
        var code1 = dialog.findViewById<TextView>(R.id.code)
        var validity1 = dialog.findViewById<TextView>(R.id.validity)
        var terms1 = dialog.findViewById<TextView>(R.id.offer_name)



        if(do_purchase.equals("1")){

            getdirecation_lay.visibility=View.VISIBLE
            close .visibility=View.GONE

            getdirecation_lay.setOnClickListener {
                val intent_edit_address = Intent(this, PaymentOtherTypePage::class.java)
                intent_edit_address.putExtra("offer_id", id)
                intent_edit_address.putExtra("amount", credit_amount)
                intent_edit_address.putExtra("simble", currencycode)

                //intent_edit_address.putExtra("amount", id)
                startActivityForResult(intent_edit_address, RESULT_PAYMENT_METHOD)

            }
        }else{
            close .visibility=View.VISIBLE
            getdirecation_lay.visibility=View.GONE
            close.setOnClickListener {
                var toast = Toast.makeText(mContext, "You have already availed this offer", Toast.LENGTH_LONG)
                toast.show()
                dialog.dismiss()
            }
        }


//        getdirecation_lay.setOnClickListener {
////            Toast.makeText(this, "ID value $id"+"do purches $do_purchase", Toast.LENGTH_LONG).show()
//            if(do_purchase.equals("1")) {
//
//
//                val intent_edit_address = Intent(this, PaymentOtherTypePage::class.java)
//                intent_edit_address.putExtra("offer_id", id)
//                intent_edit_address.putExtra("amount", credit_amount)
//                //intent_edit_address.putExtra("amount", id)
//                startActivityForResult(intent_edit_address, RESULT_PAYMENT_METHOD)
////            }
//            else{
//
//                var toast = Toast.makeText(mContext, "You have already availed this offer", Toast.LENGTH_LONG)
//                toast.show()
//
//            }
//        }
//        var

//        description1.setText(description)

        var profile = offer_image
        Glide.with(mContext)
            .load(profile)
            .into(station_images1)
        description1.setText(Html.fromHtml(description))
        amoun.setText(purchase_amount+currencycode)
        code1.setText(code)
        terms1.setText(name)
        validity1.setText(validity)


        dialog.show()
    }


//    fun getwalletbalance() {
//        progress_lay_profile1.visibility=View.VISIBLE
//        val serviceClass = commonapifetchservice::class.java
//        val intent = Intent(mContext, serviceClass)
//        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.offerpaymentstatuscheck))
//        mContext.startService(intent)
//    }
    fun `getpaymentkey`() {
        progress_lay_profile1.visibility=View.VISIBLE
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.offerpaymentstatuscheck))
        mContext.startService(intent)
    }

    fun offerpaymentinit(){
        

    }
    //calling customer

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun doThis(intentServiceResult: IntentServiceResult) {
        var apiName: String = intentServiceResult.apiName
        var finalresponse: String = intentServiceResult.resultValue
        if (apiName.equals(getString(R.string.offer_copen))) {
            if (finalresponse == "failed") {
                progress_lay_profile.visibility= View.GONE

//                toast(getString(R.string.api_failure_toast_label))
            } else {


                val response_json_object = JSONObject(finalresponse)
                Log.d("sdfbhsbfd", response_json_object.toString())
                val status = response_json_object.getString("status")
                if (status.equals("1")) {
                    transactionHistoryModel = ArrayList()
                    voucherHistoryModel = ArrayList()


                    var response = response_json_object.getString("response")
                    var offerList = response_json_object.getJSONArray("offers")
                    var voucherslist = response_json_object.getJSONArray("vouchers")
                    voucher_purchase_value=response_json_object.getString("voucher_purchase_value")
                    var internal_offers =response_json_object.getJSONArray("internal_offers")

                    if (offerList.length() > 0) {
                        for (g in 0 until offerList.length()) {
                            val offerList2 = offerList.getJSONObject(g)
//                            var vehicle_id = offerList2.getString("vehicle_id")
                            var id=offerList2.getString("id")
                            var name=offerList2.getString("name")
                            var code = offerList2.getString("code")
                            var purchase_amount = offerList2.getString("purchase_amount")
                            var description = offerList2.getString("description")
                            var terms = offerList2.getString("terms")
                            var banner_image = offerList2.getString("banner_image")
                            var offer_image = offerList2.getString("offer_image")
                            var swap_sequence = offerList2.getString("swap_sequence")
                            var credit_amount = offerList2.getString("credit_amount")
                            var status = offerList2.getString("status")
                            var do_purchase = offerList2.getString("do_purchase")
                            currencycode = offerList2.getString("currency_code")
//                            var currencycode = offerList2.getString("currency_code")
                            var validity = offerList2.getString("validity")

                            Glide.with(mContext)
                                .load(offer_image)
                                .into(id_proof_imageview1)

                            transactionHistoryModel?.add(OfferProoModel(
                                id = id,

                                purchase_amount = purchase_amount,
                                name=name,
                                terms=terms,
                                code=code,
                                currencycode = currencycode,
                                credit_amount = credit_amount,
                                do_purchase = do_purchase,
                                description = description,
                                banner_image = banner_image,
                                offer_image = offer_image,
                                validity = validity,
                                status = status));


                        }
                    }
                    if (voucherslist.length() > 0) {
                        for (g in 0 until voucherslist.length()) {
                            val voucherslist2 = voucherslist.getJSONObject(g)
                            var voucherid = voucherslist2.getString("id")
                            var vouchercode = voucherslist2.getString("code")
                            var voucherdiscount_type = voucherslist2.getString("discount_type")
                            var voucherdiscount_amount = voucherslist2.getString("discount_amount")
                            var voucher_name = voucherslist2.getString("voucher_name")
                            var voucher_offer_valid_date = voucherslist2.getString("offer_valid_date")


                            voucherHistoryModel?.add(VoucherModul(
                                voucherid=voucherid,
                                vouchercode =vouchercode,
                                voucherdiscount_type=voucherdiscount_type,
                                voucherdiscount_amount=voucherdiscount_amount,
                                voucher_offer_valid_date = voucher_offer_valid_date,
                                voucher_name=voucher_name
                            ))
                        }
                    }
                    progress_lay_profile1.visibility=View.GONE

                    initrecyclerviews(transactionHistoryModel!!)

                    voucherrecyclerviews(voucherHistoryModel!!)
                    


                }


            }
        }
    }

    private fun initrecyclerviews(transactionHistoryModel: ArrayList<OfferProoModel>) {
        triplist.layoutManager = LinearLayoutManager(mContext)
        tripadapater = OffersviewAdapter(mContext!!,transactionHistoryModel, object : rideCustomOnClickListener {
            override fun onItemClickListener(view: View, position: Int, rideid:String)
            {
                val offerTtem = transactionHistoryModel[position]

                offerDailog(offerTtem.id, offerTtem.credit_amount,offerTtem.do_purchase,
                    offerTtem.description,offerTtem.offer_image,offerTtem.terms,offerTtem.code,
                offerTtem.name,offerTtem.purchase_amount,offerTtem.currencycode,offerTtem.validity)
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


    private fun voucherrecyclerviews(voucherHistoryModel: ArrayList<VoucherModul>) {
        voucherlist.layoutManager = LinearLayoutManager(mContext)
        voucherapater = VoucherAdapter(mContext!!,voucherHistoryModel, object : rideCustomOnClickListener {
            override fun onItemClickListener(view: View, position: Int, rideid:String)
            {
            }
        })
        voucherlist.adapter = voucherapater
        voucherlist.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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