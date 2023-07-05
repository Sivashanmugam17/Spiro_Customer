package com.mauto.chd.ui.registeration

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mauto.chd.Modal.ServiceLocationModel
import com.mauto.chd.R
import com.mauto.chd.view_model_register_module.ServiceLocationViewModel
import com.mauto.chd.adaptersofchd.SLsectionedadapter
import com.mauto.chd.clickableInterface.CountryCustomOnClickListener
import com.mauto.chd.commonutils.AppUtils
import com.mauto.chd.commonutils.Constants
import kotlinx.android.synthetic.main.countrycodeselection.*
import java.util.*

class ServiceLocationSelection : AppCompatActivity()
{
    //variable Decalaration
    private lateinit var countryadapater: SLsectionedadapter
    lateinit var mViewModel: ServiceLocationViewModel
    private lateinit var mContext: Activity
    lateinit var fullcountryarray: ArrayList<ServiceLocationModel>
    var SLresponse:String = ""

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.countrycodeselection)
        mContext = this@ServiceLocationSelection
        getextravalue()
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

    fun getextravalue()
    {
        if (intent?.hasExtra("SLresponse")!!)
        {
            SLresponse = intent?.getStringExtra("SLresponse")!!
        }
    }

    fun editextlistenercall()
    {
        searchtext.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                mViewModel.filter(p0.toString(),fullcountryarray)
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
    private fun initrecyclerviews(countryarray: ArrayList<ServiceLocationModel>)
    {
        fullcountryarray=countryarray
        rv_todo_list.layoutManager = LinearLayoutManager(this)
        countryadapater = SLsectionedadapter(this,countryarray, object : CountryCustomOnClickListener {
            override fun onItemClickListener(view: View, position: Int,code :String,codeshortname :String,countryfullname :String)
            {
                val codestring = code
                val codeshortnamestring = codeshortname

                val intentz = Intent()
                intentz.putExtra(Constants.INTENT_OBJECT, codestring+","+codeshortnamestring+","+countryfullname)
                setResult(Activity.RESULT_OK, intentz)
                finish()
            }
        })
        rv_todo_list.adapter = countryadapater
    }
    private fun initViewModel()
    {
        mViewModel = ViewModelProviders.of(this).get(ServiceLocationViewModel::class.java)
        mViewModel.splitingarray(mContext)

        if(!SLresponse.equals(""))
        {
            mViewModel.splitlocationfromresponse(applicationContext,SLresponse)
        }

        mViewModel.countryarrayobserver().observe(this, Observer {
            initrecyclerviews(it)
        })
        mViewModel.filtercountryarrayobserver().observe(this, Observer {
            countryadapater.filterList(it)
        })
    }
}