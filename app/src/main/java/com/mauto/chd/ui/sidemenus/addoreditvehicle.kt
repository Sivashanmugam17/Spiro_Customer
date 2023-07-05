//package com.cabilyhandyforalldinedoo.chd.ui.sidemenus
//
//
//import android.app.Activity
//import android.content.Intent
//import android.os.Bundle
//import android.view.Gravity
//import android.view.View
//import android.widget.FrameLayout
//import android.widget.ImageView
//import android.widget.LinearLayout
//import android.widget.TextView
//import androidx.databinding.DataBindingUtil
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProviders
//import androidx.recyclerview.widget.LinearLayoutManager
//import cabily.handyforall.dinedoo.R
////import cabily.handyforall.dinedoo.databinding.VehiclelistpageBinding
//import com.cabilyhandyforalldinedoo.chd.Backgroundservices.commonapifetchservice
//import com.cabilyhandyforalldinedoo.chd.EventBusConnection.IntentServiceResult
//import com.cabilyhandyforalldinedoo.chd.adaptersofchd.VehicleListAdapter
//import com.cabilyhandyforalldinedoo.chd.clickableInterface.vehiclelistclick
//import com.cabilyhandyforalldinedoo.chd.commonutils.AppUtils
//import com.cabilyhandyforalldinedoo.chd.ui.registeration.LocaleAwareCompatActivity
//import com.cabilyhandyforalldinedoo.chd.ui.registeration.documentpageone
//import com.cabilyhandyforalldinedoo.chd.ui.registeration.documentpagetwo
//import com.google.android.material.bottomsheet.BottomSheetDialog
//import com.google.android.material.snackbar.Snackbar
////import kotlinx.android.synthetic.main.vehiclelistpage.*
//import org.greenrobot.eventbus.EventBus
//import org.greenrobot.eventbus.Subscribe
//import org.greenrobot.eventbus.ThreadMode
//class addoreditvehicle : LocaleAwareCompatActivity()
//{
//    lateinit var binding: VehiclelistpageBinding
//    private lateinit var vehiclelistadapter: VehicleListAdapter
//    private lateinit var viewModel: vehiclelistviewmodel
//    private lateinit var mContext: Activity
//    var rideid:String=""
//    var editbottomSheetDialog: BottomSheetDialog? = null
//    var useridtosend:String=""
//    var editypes:Int = 0
//    var vehicelid:String = ""
//    var vehicelmakemodel:String = ""
//
//    override fun onCreate(savedInstanceState: Bundle?)
//    {
//        super.onCreate(savedInstanceState)
////        binding = DataBindingUtil.setContentView(this, R.layout.vehiclelistpage)
//        mContext = this@addoreditvehicle
//        initviewmodel()
//        binding.setViewModel(viewModel)
//    }
//    fun getvehicledata()
//    {
//        if(AppUtils.isNetworkConnected(mContext))    getvehiclelist()
//        else commsnackbaralert(getString(R.string.failedproblem))
//    }
//    fun editoption(view:View)
//    {
//        chooseoptioneditalone(vehicelmakemodel,vehicelid)
//    }
//    fun initviewmodel()
//    {
//        viewModel = ViewModelProviders.of(this).get(vehiclelistviewmodel::class.java)
//        viewModel.closemodelobserver().observe(this, Observer {
//            finish()
//        })
//        viewModel.errormessageobserver().observe(this, Observer {
//            commsnackbaralert(it)
//        })
//        viewModel.doscumentaddedobserver().observe(this, Observer {
//           movetodocumentpage()
//        })
//        viewModel.successobserver().observe(this, Observer {
//            commsnackbaralert(it)
//            getvehicledata()
//
//        })
//        viewModel.addnewvehicleobserver().observe(this, Observer {
//            if(AppUtils.isNetworkConnected(mContext))   newvehicleadd()
//            else commsnackbaralert(getString(R.string.failedproblem))
//        })
//        viewModel.vehiclearrayobserver().observe(this, Observer {
//            if(it.size > 0)
//            {
//                othervehicle.visibility = View.VISIBLE
//                val linearLayoutManager = LinearLayoutManager(mContext)
//                linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
//                messages.layoutManager = linearLayoutManager
//                vehiclelistadapter = VehicleListAdapter(it, object : vehiclelistclick {
//                    override fun onRecyclerViewItemClick(view: View, maker: String,vehicelid:String,type:String) {
//                        chooseoption(maker,vehicelid)
//                    }
//                })
//                messages.adapter = vehiclelistadapter
//            }
//            else
//            {
//                othervehicle.visibility = View.GONE
//            }
//        })
//        viewModel.basicvehicleinfoobserver().observe(this, Observer {
//            val intent2 = Intent(mContext, editdocumentpageone::class.java)
//            startActivity(intent2)
//        })
//        viewModel.defaultvehiclemodelobserver().observe(this, Observer {
//            defaultvehicle.visibility = View.VISIBLE
//            vehicle_maker.text = it.vehicle_maker
//            vehicle_model.text = "("+it.vehicle_model+")"
//            vehicle_number.text = it.vehicle_number
//
//            vehicelmakemodel= it.vehicle_maker+"("+it.vehicle_model+")"
//            vehicelid = it.vehicle_id.toString()
//
//
//            if(it.verify_status.equals("No"))
//                verfiystatus.visibility = View.VISIBLE
//            else
//                verfiystatus.visibility = View.GONE
//        })
//    }
//    override fun onResume()
//    {
//        super.onResume()
//        if(!EventBus.getDefault().isRegistered(this))
//            EventBus.getDefault().register(this)
//
//        getvehicledata()
//    }
//    override fun onDestroy()
//    {
//        super.onDestroy()
//        if(EventBus.getDefault().isRegistered(this))
//            EventBus.getDefault().unregister(this)
//    }
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun doThis(intentServiceResult: IntentServiceResult)
//    {
//
//        var apiName: String = intentServiceResult.apiName
//        if (apiName.equals(getString(R.string.getvehiclelist)))
//        {
//            iv_line.visibility = View.GONE
//            var response: String = intentServiceResult.resultValue
//            if (response != "failed")
//                viewModel.splitresponse(applicationContext,response)
//            else
//                commsnackbaralert(getString(R.string.failed))
//        }
//        else  if (apiName.equals(getString(R.string.defaultvehicleupdte)))
//        {
//            var response: String = intentServiceResult.resultValue
//            if (response != "failed")
//            {
//                viewModel.splitresponsedefaultupdate(applicationContext,response)
//            }
//            else
//            {
//                iv_line.visibility = View.GONE
//                commsnackbaralert(getString(R.string.failed))
//            }
//        }
//        else  if (apiName.equals(getString(R.string.getvehicleinfoforedit)))
//        {
//            iv_line.visibility = View.GONE
//            var response: String = intentServiceResult.resultValue
//            if (response != "failed")
//            {
//                if(editypes == 1)
//                  viewModel.splitvehicleinfo(applicationContext,response)
//                else if(editypes == 2)
//                {
//                    viewModel.deletedb(mContext)
//                    viewModel.splitdocumentinfo(applicationContext,response)
//                }
//
//            }
//            else
//            {
//                commsnackbaralert(getString(R.string.failed))
//            }
//        }
//    }
//    //common error notification page
//    fun commsnackbaralert(message:String)
//    {
//        val snack = Snackbar.make(toppage,message, Snackbar.LENGTH_LONG)
//        var view: View = snack.getView()
//        var params: FrameLayout.LayoutParams = FrameLayout.LayoutParams(view.getLayoutParams())
//        params.gravity = Gravity.TOP;
//        view.setLayoutParams(params)
//        snack.show()
//    }
//    fun getvehiclelist()
//    {
//        iv_line.visibility = View.VISIBLE
//        val serviceClass = commonapifetchservice::class.java
//        val intent = Intent(mContext, serviceClass)
//        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.getvehiclelist))
//        mContext.startService(intent)
//    }
//    fun markdefaultvehicle(vehicleid:String)
//    {
//        iv_line.visibility = View.VISIBLE
//        val serviceClass = commonapifetchservice::class.java
//        val intent = Intent(mContext, serviceClass)
//        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.defaultvehicleupdte))
//        intent.putExtra("vehicleid", vehicleid)
//        mContext.startService(intent)
//    }
//    fun getvehicleinfo(vehicleid:String)
//    {
//        iv_line.visibility = View.VISIBLE
//        val serviceClass = commonapifetchservice::class.java
//        val intent = Intent(mContext, serviceClass)
//        intent.putExtra(mContext.getString(R.string.intent_putextra_api_key), mContext.getString(R.string.getvehicleinfoforedit))
//        intent.putExtra("vehicleid", vehicleid)
//        mContext.startService(intent)
//    }
//    fun newvehicleadd()
//    {
//        viewModel.cleardocumentdata(mContext)
//        val intent2 = Intent(mContext, documentpageone::class.java)
//        intent2.putExtra("getfromadd", "1")
//        startActivity(intent2)
//    }
//    fun movetodocumentpage()
//    {
//        val intent2 = Intent(mContext, documentpagetwo::class.java)
//        intent2.putExtra("getfromadd", "1")
//        startActivity(intent2)
//    }
//    fun vehicleselect(vehiclemake:String,vehicleid:String)
//    {
//        val bottomSheetDialog =  BottomSheetDialog(this)
//        val view = getLayoutInflater().inflate(R.layout.vehicleapproval, null);
//        bottomSheetDialog.setContentView(view)
//        val title = view.findViewById(R.id.title) as TextView
//        val subcontent = view.findViewById(R.id.subcontent) as TextView
//        val close = view.findViewById(R.id.close) as LinearLayout
//        val ok = view.findViewById(R.id.ok) as LinearLayout
//        title.setText(getString(R.string.choosedefaultvehicle))
//        subcontent.setText(getString(R.string.choosedefaultvehiclecontent)+" "+vehiclemake+" "+getString(R.string.choosedefaultvehiclecontent2))
//        close.setOnClickListener { bottomSheetDialog.dismiss() }
//        ok.setOnClickListener {
//            bottomSheetDialog.dismiss()
//            markdefaultvehicle(vehicleid)
//        }
//        bottomSheetDialog.show()
//    }
//    fun editvehiclesheet(vehiclemake:String,vehicleid:String)
//    {
//            editbottomSheetDialog =  BottomSheetDialog(this)
//            val view = getLayoutInflater().inflate(R.layout.editvehicledetails, null);
//            editbottomSheetDialog!!.setContentView(view)
//            editbottomSheetDialog!!.setCancelable(true)
//            val title = view.findViewById(R.id.title) as TextView
//
//
//        val basicedit = view.findViewById(R.id.basicedit) as TextView
//        val documentedit = view.findViewById(R.id.documentedit) as TextView
//
//        val basiceditimage = view.findViewById(R.id.basiceditimage) as ImageView
//        val documeteditimage = view.findViewById(R.id.documeteditimage) as ImageView
//
//
//
//            val close = view.findViewById(R.id.close) as LinearLayout
//
//
//            title.setText(getString(R.string.editvehicle)+" "+vehiclemake)
//        documentedit.setOnClickListener {
//                editbottomSheetDialog!!.dismiss()
//                editypes = 2
//                getvehicleinfo(vehicleid)
//            }
//
//        basiceditimage.setOnClickListener {
//            editbottomSheetDialog!!.dismiss()
//            editypes = 1
//            getvehicleinfo(vehicleid)
//        }
//
//        documeteditimage.setOnClickListener {
//            editbottomSheetDialog!!.dismiss()
//            editypes = 2
//            getvehicleinfo(vehicleid)
//        }
//
//
//        basicedit.setOnClickListener {
//                editbottomSheetDialog!!.dismiss()
//                editypes = 1
//                getvehicleinfo(vehicleid)
//            }
//        close.setOnClickListener {
//            editbottomSheetDialog!!.dismiss()
//        }
//            editbottomSheetDialog!!.show()
//    }
//    fun chooseoption(vehiclemake:String,vehicleid:String)
//    {
//        val choosedialog =  BottomSheetDialog(this)
//        val view = getLayoutInflater().inflate(R.layout.chooseoption, null);
//        choosedialog!!.setContentView(view)
//        choosedialog!!.setCancelable(true)
//        val title = view.findViewById(R.id.title) as TextView
//        val makeasdefault = view.findViewById(R.id.makeasdefault) as TextView
//        val editvehicle = view.findViewById(R.id.editvehicle) as TextView
//
//        val makeasdefaultimage = view.findViewById(R.id.makeasdefaultimage) as ImageView
//        val editimage = view.findViewById(R.id.editimage) as ImageView
//
//
//        val close = view.findViewById(R.id.close) as LinearLayout
//        title.setText(vehiclemake)
//
//        makeasdefaultimage.setOnClickListener {
//            choosedialog!!.dismiss()
//            vehicleselect(vehiclemake,vehicleid)
//        }
//        makeasdefault.setOnClickListener {
//            choosedialog!!.dismiss()
//            vehicleselect(vehiclemake,vehicleid)
//        }
//        editimage.setOnClickListener {
//            choosedialog!!.dismiss()
//            editvehiclesheet(vehiclemake,vehicleid)
//        }
//        editvehicle.setOnClickListener {
//            choosedialog!!.dismiss()
//            editvehiclesheet(vehiclemake,vehicleid)
//        }
//        close.setOnClickListener {
//            choosedialog!!.dismiss()
//        }
//        choosedialog!!.show()
//    }
//    fun chooseoptioneditalone(vehiclemake:String,vehicleid:String)
//    {
//        val choosedialog =  BottomSheetDialog(this)
//        val view = getLayoutInflater().inflate(R.layout.chooseoptioneditalone, null);
//        choosedialog!!.setContentView(view)
//        choosedialog!!.setCancelable(true)
//        val title = view.findViewById(R.id.title) as TextView
//        val editvehicle = view.findViewById(R.id.editvehicle) as TextView
//        val editimage = view.findViewById(R.id.editimage) as ImageView
//        val close = view.findViewById(R.id.close) as LinearLayout
//        title.setText(vehiclemake)
//        editimage.setOnClickListener {
//            choosedialog!!.dismiss()
//            editvehiclesheet(vehiclemake,vehicleid)
//        }
//        editvehicle.setOnClickListener {
//            choosedialog!!.dismiss()
//            editvehiclesheet(vehiclemake,vehicleid)
//        }
//        close.setOnClickListener {
//            choosedialog!!.dismiss()
//        }
//        choosedialog!!.show()
//    }
//}