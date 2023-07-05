package com.mauto.chd.ui.registeration

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mauto.chd.event_bus_connection.IntentServiceResult
import com.mauto.chd.Modal.VehicleselectionModel
import com.mauto.chd.Modal.VehicleselectionModels
import com.mauto.chd.Modal.VehicleselectionModeltype
import com.mauto.chd.R
import com.mauto.chd.SessionManagerPackage.SessionManager
import com.mauto.chd.view_model_register_module.VehicleDetailsViewModel
import com.mauto.chd.adaptersofchd.vechileselectionadapter
import com.mauto.chd.adaptersofchd.vechileselectionmakeadapter
import com.mauto.chd.adaptersofchd.vechileselectionmodeladapter
import com.mauto.chd.adaptersofchd.vechileselectionyearadapter
import com.mauto.chd.clickableInterface.CustomOnClickListener
import com.mauto.chd.commonutils.AppUtils
import com.mauto.chd.commonutils.Constants
import kotlinx.android.synthetic.main.vehiclemodelselction.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*

class VehicleDetailsSelection : LocaleAwareCompatActivity() {
    //variable Decalaration
    private lateinit var countryadapater: vechileselectionadapter
    private var makeadapater: vechileselectionmakeadapter? = null
    private lateinit var my_rv_todo_list : RecyclerView
    private lateinit var modeladapater: vechileselectionmodeladapter
    private lateinit var yearadapater: vechileselectionyearadapter



    lateinit var mViewModel: VehicleDetailsViewModel
    private lateinit var mContext: Activity
    var fullcountryarray: ArrayList<VehicleselectionModel>? = null
    var makefullcountryarray: ArrayList<VehicleselectionModels>? = null
    var modelfullcountryarray: ArrayList<VehicleselectionModeltype>? = null
    var yearfullcountryarray: ArrayList<String>? = null




    private lateinit var condition: String
    private lateinit var locationid: String
    private  var categoryid: String = ""
    private var skipingid: String = ""
    private lateinit var vehicletypeid: String
    private lateinit var makeid: String
    private lateinit var modelid: String
    var gotservicelocationresponse: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vehiclemodelselction)
        mContext = this@VehicleDetailsSelection
        my_rv_todo_list = findViewById(R.id.rv_todo_list) as RecyclerView
        getExtraValue()
        initViewModel()
        editextlistenercall()

        // click listener
        close.setOnClickListener {
            AppUtils.hideKeyboard(mContext, clear!!)
            finish()
        }
        clear.setOnClickListener {
            searchtext.getText().clear()
            AppUtils.hideKeyboard(mContext, clear!!)
        }
    }


    private fun getExtraValue() {
        condition = intent.getStringExtra("vehicled")!!
//        locationid = intent.getStringExtra("locationid")
//        locationid = intent.getStringExtra("locationid")
//        skipingid = intent.getStringExtra("skipingid")
//        categoryid = intent.getStringExtra("categoryid")
//        vehicletypeid = intent.getStringExtra("vehicletypeid")
//        makeid = intent.getStringExtra("makeid")
//        modelid = intent.getStringExtra("modelid")
    }

    fun editextlistenercall() {
        searchtext.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (fullcountryarray != null && fullcountryarray!!.size > 0) {
                    mViewModel.filter(p0.toString(), fullcountryarray!!)
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0!!.length == 0)
                    clear.visibility = View.INVISIBLE
                else
                    clear.visibility = View.VISIBLE
            }
        })
    }

    private fun initrecyclerviews(countryarray: ArrayList<VehicleselectionModel>) {
        fullcountryarray = countryarray
        my_rv_todo_list.layoutManager = LinearLayoutManager(this)
        countryadapater = vechileselectionadapter(this, countryarray, object : CustomOnClickListener {
            override fun onItemClickListener(view: View, position: Int, id: String, name: String) {
                val intentz = Intent()
                intentz.putExtra(Constants.INTENT_OBJECT, id + "," + name)
                setResult(Activity.RESULT_OK, intentz)
                finish()
            }
        })
        my_rv_todo_list.adapter = countryadapater
    }
    private fun initrecyclerviewmake(makemodelarray: ArrayList<VehicleselectionModels>) {
        makefullcountryarray = makemodelarray
        my_rv_todo_list.layoutManager = LinearLayoutManager(this)
        makeadapater = vechileselectionmakeadapter(this, makemodelarray, object : CustomOnClickListener {
            override fun onItemClickListener(view: View, position: Int, id: String, name: String) {
                val intentz = Intent()
                intentz.putExtra(Constants.INTENT_OBJECT, id + "," + name)
                setResult(Activity.RESULT_OK, intentz)
                finish()
            }
        })
        my_rv_todo_list.adapter = makeadapater
    }

    private fun initrecyclerviewvehiclemodel(modelarraylist: ArrayList<VehicleselectionModeltype>) {
        modelfullcountryarray = modelarraylist
        my_rv_todo_list.layoutManager = LinearLayoutManager(this)
        modeladapater = vechileselectionmodeladapter(this, modelarraylist, object : CustomOnClickListener {
            override fun onItemClickListener(view: View, position: Int, id: String, name: String) {
                val intentz = Intent()
                intentz.putExtra(Constants.INTENT_OBJECT, id + "," + name)
                setResult(Activity.RESULT_OK, intentz)
                finish()
            }
        })
        my_rv_todo_list.adapter = modeladapater
    }
    private fun initrecyclerviewvehicleyear(yeararraylist: ArrayList<String>) {
        yearfullcountryarray = yeararraylist
        my_rv_todo_list.layoutManager = LinearLayoutManager(this)
        yearadapater = vechileselectionyearadapter(this, yeararraylist, object : CustomOnClickListener {
            override fun onItemClickListener(view: View, position: Int, id: String, name: String) {
                val intentz = Intent()
                intentz.putExtra(Constants.INTENT_OBJECT, id + "," + name)
                setResult(Activity.RESULT_OK, intentz)
                finish()
            }
        })
        my_rv_todo_list.adapter = yearadapater
    }



    private fun initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(VehicleDetailsViewModel::class.java)
        if (condition.equals("1")) {
            if (gotservicelocationresponse.equals("")) {
//                loader.visibility = View.VISIBLE
                mViewModel.getservicelocation(mContext)
            } else {
                mViewModel.splilocationresponse(mContext, gotservicelocationresponse, skipingid)
            }
        } else if (condition.equals("2")) {
//            loader.visibility = View.VISIBLE
            mViewModel.startcategoryfetchapi(mContext)
        } else if (condition.equals("3")) {
//            loader.visibility = View.VISIBLE
            var mSessionManager: SessionManager? = null
            mSessionManager = SessionManager(mContext)
            if (mSessionManager!!.getCategoryDetails().equals("")) {
                mViewModel.startcategoryapi(mContext)
            } else {
////                mViewModel.fullcategoryresponse(mContext, categoryid)
            }
        } else if (condition.equals("4")) {
//            loader.visibility = View.VISIBLE
//            mViewModel.fullcategorymakeresponse(mContext, vehicletypeid)
            mViewModel.getvehiclemake(mContext)
        } else if (condition.equals("5")) {
//            loader.visibility = View.VISIBLE
            mViewModel.getvehiclemake(mContext)
        } else if (condition.equals("6")) {
//            loader.visibility = View.VISIBLE
//            mViewModel.fullcategoryyearresponse(mContext, makeid, categoryid, modelid)
            mViewModel.getvehiclemake(mContext)

        }

        mViewModel.countryarrayobserver().observe(this, Observer {
//            if (loader.getVisibility() == View.VISIBLE)
//                loader.visibility = View.INVISIBLE
            initrecyclerviews(it)
        })
        if (condition.equals("4")){

            mViewModel.countryarrayobservers().observe(this, Observer {
//                if (loader.getVisibility() == View.VISIBLE)
//                    loader.visibility = View.INVISIBLE
                initrecyclerviewmake(it)
            })
}
      if (condition.equals("5")){
          mViewModel.vechilemodelobservers().observe(this, Observer {
//              if (loader.getVisibility() == View.VISIBLE)
//                  loader.visibility = View.INVISIBLE
              initrecyclerviewvehiclemodel(it)
          })

      }
        if (condition.equals("6")){
            mViewModel.yearmodelobservers().observe(this, Observer {
//                if (loader.getVisibility() == View.VISIBLE)
//                    loader.visibility = View.INVISIBLE
                initrecyclerviewvehicleyear(it)
            })

        }




        mViewModel.filtercountryarrayobserver().observe(this, Observer {
            countryadapater.filterList(it)
        })
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun doThis(intentServiceResult: IntentServiceResult) {
        val finalresponse = intentServiceResult.resultValue
//        loader.visibility = View.INVISIBLE
        var apiName: String = intentServiceResult.apiName
        if (apiName.equals(getString(R.string.getmakemodelyear))) {
            if (finalresponse != "failed") {
                if (condition.equals("2")) {
                    mViewModel.splitresponsecategory(mContext, finalresponse, locationid, skipingid)
                }
            } else
                commonerrorpage()
        } else if (apiName.equals(getString(R.string.getservicelocationresponse))) {
            if (finalresponse != "failed") {
                if (condition.equals("1")) {
                    gotservicelocationresponse = finalresponse
                    mViewModel.splilocationresponse(mContext, finalresponse, skipingid)
                }
            } else
                commonerrorpage()
        } else if (apiName.equals(getString(R.string.getonlymake))) {
            if (finalresponse != "failed") {
                mViewModel.fullcategoryresponse(mContext, categoryid)
            } else
                commonerrorpage()
        }
    }


    fun commonerrorpage() {
        AppUtils.commonerrorsheet(mContext, getString(R.string.failed), getString(R.string.failedproblem))
    }

    override fun onPause() {
        super.onPause()
        EventBus.getDefault().unregister(this);
    }

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().register(this);
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this);
    }


}
