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
import com.mauto.chd.Modal.CountryselectionModel
import com.mauto.chd.R
import com.mauto.chd.view_model_register_module.CountrycodeselectionViewModel
import com.mauto.chd.adaptersofchd.countrysectionedadapter
import com.mauto.chd.clickableInterface.CountryCustomOnClickListener
import com.mauto.chd.commonutils.AppUtils
import com.mauto.chd.commonutils.Constants
import kotlinx.android.synthetic.main.countrycodeselection.*
import java.util.*

class CountryCodeSelection : LocaleAwareCompatActivity()
{
    //variable Decalaration
    private lateinit var countryadapater: countrysectionedadapter
    lateinit var mViewModel: CountrycodeselectionViewModel
    private lateinit var mContext: Activity
    lateinit var fullcountryarray: ArrayList<CountryselectionModel>
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.countrycodeselection)
        mContext = this@CountryCodeSelection
        initViewModel()
        editextlistenercall()
        // click listener
        close.setOnClickListener {
            AppUtils.hideKeyboard(mContext, searchtext!!)
           finish()
        }
        clear.setOnClickListener {
            searchtext.getText().clear()
            AppUtils.hideKeyboard(mContext, searchtext!!)
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
    private fun initrecyclerviews(countryarray: ArrayList<CountryselectionModel>)
    {
        fullcountryarray=countryarray
        rv_todo_list.layoutManager = LinearLayoutManager(this)
        countryadapater = countrysectionedadapter(this,countryarray, object : CountryCustomOnClickListener {
            override fun onItemClickListener(view: View, position: Int,code :String,codeshortname :String,countryfullname :String)
            {
                AppUtils.hideKeyboard(mContext, searchtext!!)
                val codestring = code
                val codeshortnamestring = codeshortname
                val intentz = Intent()
                intentz.putExtra(Constants.INTENT_OBJECT, codestring+","+codeshortnamestring+","+countryfullname)
                setResult(Activity.RESULT_FIRST_USER, intentz)
                finish()
            }
        })
        rv_todo_list.adapter = countryadapater
    }
    private fun initViewModel()
    {
        mViewModel = ViewModelProviders.of(this).get(CountrycodeselectionViewModel::class.java)
        mViewModel.splitingarray(mContext)
        mViewModel.countryarrayobserver().observe(this, Observer {
            initrecyclerviews(it)
        })
        mViewModel.filtercountryarrayobserver().observe(this, Observer {
            countryadapater.filterList(it)
        })
    }
}