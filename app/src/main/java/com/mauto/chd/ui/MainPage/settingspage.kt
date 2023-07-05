//package com.cabilyhandyforalldinedoo.chd.ui.MainPage
//
//import android.app.Activity
//import android.content.Intent
//import android.net.Uri
//import android.os.Bundle
//import android.view.View
//import android.widget.ImageView
//import android.widget.LinearLayout
//import android.widget.TextView
//import androidx.appcompat.app.AppCompatActivity
//import androidx.databinding.DataBindingUtil
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProviders
//import cabily.handyforall.dinedoo.R
////import cabily.handyforall.dinedoo.databinding.SettingspageBinding
//import com.cabilyhandyforalldinedoo.chd.Backgroundservices.nearningcustomerlocation
//import com.cabilyhandyforalldinedoo.chd.Locales
//import com.cabilyhandyforalldinedoo.chd.ViewModelTrackingPage.settingspageviewmodel
//import com.cabilyhandyforalldinedoo.chd.ui.registeration.LocaleAwareCompatActivity
//import com.cabilyhandyforalldinedoo.chd.ui.registeration.splashpage
//import com.google.android.material.bottomsheet.BottomSheetDialog
////import kotlinx.android.synthetic.main.settingspage.*
//
//class settingspage : LocaleAwareCompatActivity ()
//{
//    private lateinit var mContext: Activity
//    lateinit var binding: SettingspageBinding
//    var defaultnaviagtion:String="1"
//    private lateinit var viewModel: settingspageviewmodel
//    override fun onCreate(savedInstanceState: Bundle?)
//    {
//        super.onCreate(savedInstanceState)
////        setContentView(R.layout.settingspage)
//        binding = DataBindingUtil.setContentView(this, R.layout.settingspage)
//        mContext = this@settingspage
//        initviewmodel()
//        binding.setViewModel(viewModel)
//        if (intent?.hasExtra("hidelanguage")!!)
//        {
//            langlayoutline.visibility = View.GONE
//            langlayout.visibility = View.GONE
//        }
//        else
//        {
//            langlayoutline.visibility = View.INVISIBLE
//            langlayout.visibility = View.VISIBLE
//        }
//        reroutingswitch.setOnCheckedChangeListener { buttonView, isChecked ->
//            viewModel.reroutingoption(mContext,isChecked)
//        }
//        nightmode.setOnCheckedChangeListener { buttonView, isChecked ->
//            viewModel.nightmodeoption(mContext,isChecked)
//        }
//        close.setOnClickListener {
//            finish()
//        }
//        l1.setOnClickListener {
//            chooselanguage()
//        }
//        l2.setOnClickListener {
//            chooselanguage()
//        }
//        l3.setOnClickListener {
//            chooselanguage()
//        }
//        languageselected.setOnClickListener {
//            chooselanguage()
//        }
//
//    }
//    //Viewmodel Observer Part
//    fun initviewmodel()
//    {
//        viewModel = ViewModelProviders.of(this).get(settingspageviewmodel::class.java)
//        viewModel.getreroutingoption(mContext)
//        viewModel.getnightmodestyle(mContext)
//        viewModel.getnavigationoption(mContext)
//        viewModel.getlanguagechoosen(mContext)
//        viewModel.reroutingoptionsettingsobserver().observe(this, Observer {
//            reroutingswitch.isChecked = it
//         })
//
//        viewModel.nighmodesettingsobeser().observe(this, Observer {
//            nightmode.isChecked = it
//        })
//        viewModel.navigatevalueobserver().observe(this, Observer {
//            defaultnaviagtion = it
//           if(it.equals("1"))
//               navigatetext.text =getString(R.string.goolge_map)
//            else
//               navigatetext.text = getString(R.string.waze)
//        })
//        viewModel.languageoptionobserver().observe(this, Observer {
//            if(it.equals("1"))
//                languageselected.text =getString(R.string.tamil)
//            else  if(it.equals("2"))
//                languageselected.text = getString(R.string.english)
//            else  if(it.equals("3"))
//                languageselected.text = getString(R.string.arabic)
//        })
//        viewModel.navigateobserver().observe(this, Observer {
//            navigationpage(defaultnaviagtion)
//        })
//    }
//    fun navigationpage(navigatechoosen:String)
//    {
//        var navigationdialog =  BottomSheetDialog(this)
//        val view = getLayoutInflater().inflate(R.layout.navigationchange, null);
//        navigationdialog!!.setContentView(view)
//        navigationdialog!!.setCancelable(true)
//        val gmappostion = view.findViewById(R.id.gmappostion) as ImageView
//        val wazepostion = view.findViewById(R.id.wazepostion) as ImageView
//        if(navigatechoosen.equals("1"))
//        {
//            gmappostion.visibility = View.VISIBLE
//            wazepostion.visibility = View.INVISIBLE
//        }
//        else
//        {
//            gmappostion.visibility = View.INVISIBLE
//            wazepostion.visibility = View.VISIBLE
//        }
//        val googleclickname = view.findViewById(R.id.googleclickname) as TextView
//        val wazeclickname = view.findViewById(R.id.wazeclickname) as TextView
//        val googleclicknamedesc = view.findViewById(R.id.googleclicknamedesc) as TextView
//        val wazeclickdesc = view.findViewById(R.id.wazeclickdesc) as TextView
//        var statuswaze:Int=openWaze()
//        if(statuswaze == 0)
//            wazeclickdesc.text = getString(R.string.waze_not_available)
//        googleclickname.setOnClickListener {
//            changevaluefornavigation("1")
//            navigationdialog!!.dismiss()
//        }
//        wazeclickname.setOnClickListener {
//            changevaluefornavigation("2")
//            navigationdialog!!.dismiss()
//        }
//        googleclicknamedesc.setOnClickListener {
//            changevaluefornavigation("1")
//            navigationdialog!!.dismiss()
//        }
//        wazeclickdesc.setOnClickListener {
//            changevaluefornavigation("2")
//            navigationdialog!!.dismiss()
//        }
//        val cloepage = view.findViewById(R.id.cloepage) as LinearLayout
//        cloepage.setOnClickListener {
//            navigationdialog!!.dismiss()
//        }
//        navigationdialog!!.show()
//    }
//    fun changevaluefornavigation(value:String)
//    {
//        viewModel.navigationchoosen(mContext,value)
//        viewModel.getnavigationoption(mContext)
//    }
//    fun openWaze():Int {
//        var status:Int =0
//        packageManager?.let {
//            val url = "waze://?ll=&navigate=yes"
//            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//            intent.resolveActivity(it)?.let {
//                status = 1
//            } ?: run {
//                status = 0
//            }
//        }
//        return status
//    }
//    fun chooselanguage()
//    {
//        var languagedialog =  BottomSheetDialog(this)
//        val view = getLayoutInflater().inflate(R.layout.chooselanguagesheet, null);
//        languagedialog!!.setContentView(view)
//        languagedialog!!.setCancelable(true)
//        val cloepage = view.findViewById(R.id.cloepageoflanguage) as LinearLayout
//        val englishchoosen = view.findViewById(R.id.englishchoosen) as TextView
//        val tamilchoosen = view.findViewById(R.id.tamilchoosen) as TextView
//        val urduchoosen = view.findViewById(R.id.urduchoosen) as TextView
//        englishchoosen.setOnClickListener {
//            changevalueforlanguage("2")
//            languagedialog!!.dismiss()
//            updateLocale(Locales.English)
//        }
//        tamilchoosen.setOnClickListener {
//            changevalueforlanguage("1")
//            languagedialog!!.dismiss()
//            updateLocale(Locales.Tamil)
//        }
//        urduchoosen.setOnClickListener {
//            changevalueforlanguage("3")
//            languagedialog!!.dismiss()
//            updateLocale(Locales.Urdu)
//        }
//        cloepage.setOnClickListener {
//            languagedialog!!.dismiss()
//        }
//        languagedialog!!.show()
//    }
//    fun changevalueforlanguage(value:String)
//    {
//        viewModel.languagechoosen(mContext,value)
//        viewModel.getlanguagechoosen(mContext)
//    }
//}