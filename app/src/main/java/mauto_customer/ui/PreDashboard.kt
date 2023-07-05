package mauto_customer.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mauto.chd.BuildConfig
import com.mauto.chd.Modal.TransactionHistoryModel
import com.mauto.chd.R
import com.mauto.chd.SessionManagerPackage.SessionManager
import com.mauto.chd.adaptersofchd.wallettransactionadapter
import com.mauto.chd.backgroundservices.commonapifetchservice
import com.mauto.chd.backgroundservices.logoutcallapi
import com.mauto.chd.clickableInterface.rideCustomOnClickListener
import com.mauto.chd.event_bus_connection.IntentServiceResult
import com.mauto.chd.ui.registeration.splashpage
import com.mauto.chd.ui.sidemenus.WalletPageNew
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_wallet.*
import kotlinx.android.synthetic.main.predashboard_activity.*
import kotlinx.android.synthetic.main.predashboard_activity.appversionwallet
import kotlinx.android.synthetic.main.service_request.*
import mauto_customer.ui.adapter.Modeladapter
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.toast
import org.json.JSONArray

class PreDashboard : AppCompatActivity() {


    override fun onPause() {
        super.onPause()
        EventBus.getDefault().unregister(this);
    }
    override fun onResume() {
        super.onResume()
        if (mSessionManager!!.getselected_model().equals("")){
            if (mSessionManager!!.getcurrentmodelname().equals("")){
                model_lay_two.visibility=View.GONE
                model_lay_one.visibility=View.VISIBLE
                prebookings_textviews.visibility=View.GONE
                Confirmbutton.visibility=View.VISIBLE
            }else{
                model_lay_two.visibility=View.VISIBLE
                model_lay_one.visibility=View.GONE
                prebookings_textviews.visibility=View.VISIBLE
                Confirmbutton.visibility=View.GONE
                textView12.setText(mSessionManager!!.getselected_model())

            }

        }else{
            model_lay_two.visibility=View.VISIBLE
            model_lay_one.visibility=View.GONE
            prebookings_textviews.visibility=View.VISIBLE
            Confirmbutton.visibility=View.GONE
            textView12.setText(mSessionManager!!.getselected_model())
        }

        EventBus.getDefault().register(this);
    }
    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this);
    }

    private var mSessionManager: SessionManager? = null
    var mModelList = ArrayList<String>()
    lateinit var modellist: RecyclerView
    private lateinit var tripadapater: Modeladapter
    private lateinit var mContext: Activity
    var modelnames:String = ""
    var app_version:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.predashboard_activity)
        mContext = this@PreDashboard
        mSessionManager = SessionManager(this)
        mModelList = mSessionManager!!.getModelPojo()
        app_version= BuildConfig.VERSION_NAME
        appversionwallet.setText("V "+app_version)
//        if (mSessionManager!!.getselected_model().equals("")){
//            model_lay_two.visibility=View.GONE
//            model_lay_one.visibility=View.VISIBLE
//            prebookings_textviews.visibility=View.GONE
//            Confirmbutton.visibility=View.VISIBLE
//
//        }else{
//            model_lay_two.visibility=View.VISIBLE
//            model_lay_one.visibility=View.GONE
//            prebookings_textviews.visibility=View.VISIBLE
//            Confirmbutton.visibility=View.GONE
//            textView12.setText(mSessionManager!!.getselected_model())
//        }
        initialize()
        println("---$$ ${mModelList.size}")
        Confirmbutton.setOnClickListener{
            if (modelnames.isEmpty()){
                toast("Select Your Model ")
            }else{
                getprebookingsubmit()

            }

        }
        logoout.setOnClickListener{
            exitappalert()
        }
        prebookings_textviews.setOnClickListener{
            val intent2 = Intent(mContext, WalletPageNew()::class.java)
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            mContext.startActivity(intent2)
        }
    }
    fun initialize() {
        val list=mSessionManager!!.getmodellist()
        modellist = findViewById(R.id.modellist)
        lead_name.setText(mSessionManager!!.getleadname())
        lead_number.setText(mSessionManager!!.getleadid())
        initrecyclerviews(list!!)

    }
    fun exitappalert() {
        val builder = AlertDialog.Builder(mContext)
        builder.setTitle(getString(R.string.confirm_cancel))
        builder.setMessage(getString(R.string.wanttoexit))
        builder.setPositiveButton(getString(R.string.yesia)) { dialog, which ->
            mSessionManager = SessionManager(applicationContext!!)
            mSessionManager!!.clearalldata()
            backtologin(applicationContext)
            logout(mContext)

        }
        builder.setNegativeButton(getString(R.string.closepage)) { dialog, which ->
        }
        builder.show()
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

    fun logout(mcontext: Context) {
        val serviceClass = logoutcallapi::class.java
        val intent = Intent(mcontext, serviceClass)
        intent.putExtra(mcontext.getString(R.string.intent_putextra_api_key), mcontext.getString(R.string.logoutcallapi))
        mcontext.startService(intent)
    }



    private fun initrecyclerviews(list:String) {
        var vehicle: java.util.ArrayList<String> = java.util.ArrayList()
        val list= JSONArray(list)
        for (a in 0..list.length()-1){
            vehicle.add(list[a].toString())
        }
        modellist.layoutManager = LinearLayoutManager(mContext)
        tripadapater = Modeladapter(mContext!!,vehicle, object : rideCustomOnClickListener {
            override fun onItemClickListener(view: View, position: Int, rideid:String)
            {
                var v= view as TextView
                v.text
                modelnames=v.text.toString()

            }
        })
        modellist.adapter = tripadapater
        modellist.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

            }
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }
    fun getprebookingsubmit() {
        val serviceClass = commonapifetchservice::class.java
        val intent = Intent(mContext, serviceClass)
        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.prebooking_model))
        intent.putExtra("modelname", modelnames)
        mContext.startService(intent)
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun doThis(intentServiceResult: IntentServiceResult) {
        var apiName: String = intentServiceResult.apiName
        var finalresponse: String = intentServiceResult.resultValue
        if (apiName.equals(getString(R.string.prebooking_model))) {
            if (finalresponse == "failed") {

            }else{

                mSessionManager!!.setcurrentmodelname(modelnames)
                mSessionManager!!.setselected_model(modelnames)

                Log.d("defrtdgddry20", finalresponse)
                val intent2 = Intent(mContext, WalletPageNew()::class.java)
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                mContext.startActivity(intent2)
            }

        }
    }



}