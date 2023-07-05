package com.mauto.chd.ui.MainPage

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mauto.chd.adaptersofchd.AutoCompleteResponceAdapter
import com.mauto.chd.retrofit.RetrofitInterface
import com.dineshm.AutoCompleteWithDB.Pojo.AutoCompleteResponcePojo
import com.dineshm.AutoCompleteWithDB.Pojo.PredictionsItem
import com.mauto.chd.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList


class AutoCompleteSearchActivity : AppCompatActivity() {

    lateinit var autoComplete_searchView: SearchView
    lateinit var autoComplete_recuclerView_response: RecyclerView
    lateinit var autoComplete_recuclerView_recent: RecyclerView
    lateinit var responseArrayList: ArrayList<PredictionsItem>
    lateinit var autoComplete_noData_tv_responce: TextView


    companion object {
        lateinit var instance: AutoCompleteSearchActivity
    }

    init {
        instance = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auto_complete_search)
        supportActionBar?.hide()
        init()


        autoComplete_searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }


            @SuppressLint("SetTextI18n")
            override fun onQueryTextChange(p0: String): Boolean {
                if (p0.length < 2) {
                    responseArrayList.clear()
                    autoComplete_noData_tv_responce.visibility = View.VISIBLE
                    autoComplete_noData_tv_responce.text = "Enter Your Place"
                    autoComplete_recuclerView_response.adapter = AutoCompleteResponceAdapter(this@AutoCompleteSearchActivity, responseArrayList)
                } else {
                    responseArrayList.clear()
                    val base_url = "https://maps.googleapis.com/maps/api/"
                    val retrofit = Retrofit.Builder().baseUrl(base_url)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                    val getPlacesApi = retrofit.create(RetrofitInterface::class.java)
                    val listCall: Call<AutoCompleteResponcePojo>? = getPlacesApi.getAddressListApi(p0)
                    listCall?.enqueue(object : Callback<AutoCompleteResponcePojo> {
                        override fun onResponse(call: Call<AutoCompleteResponcePojo>, response: Response<AutoCompleteResponcePojo>) {
                            responseArrayList = response.body()?.predictions as ArrayList<PredictionsItem>
                            Log.e("Size of Array", responseArrayList.size.toString())
                            if (responseArrayList.size == 0) {
                                autoComplete_noData_tv_responce.visibility = View.VISIBLE
                                responseArrayList.clear()
                                autoComplete_recuclerView_response.adapter = AutoCompleteResponceAdapter(this@AutoCompleteSearchActivity, responseArrayList)
                                autoComplete_noData_tv_responce.text = "No Data Found"
                            } else {
                                autoComplete_noData_tv_responce.visibility = View.GONE
                                autoComplete_recuclerView_response.layoutManager = LinearLayoutManager(this@AutoCompleteSearchActivity)
                                autoComplete_recuclerView_response.adapter = AutoCompleteResponceAdapter(this@AutoCompleteSearchActivity, responseArrayList)
                            }
                        }

                        override fun onFailure(call: Call<AutoCompleteResponcePojo>, t: Throwable) {
                            Log.e("Error", t.toString())
                        }
                    })
                }
                return true
            }
        })



    }
    private fun init() {
        autoComplete_searchView = findViewById(R.id.autoComplete_searchView)
        autoComplete_recuclerView_response = findViewById(R.id.autoComplete_recuclerView_response)
        responseArrayList = ArrayList<PredictionsItem>()
        autoComplete_noData_tv_responce = findViewById(R.id.autoComplete_noData_tv_responce)
    }


}