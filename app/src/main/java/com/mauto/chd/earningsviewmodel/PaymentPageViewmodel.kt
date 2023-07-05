package com.mauto.chd.earningsviewmodel

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData



class PaymentPageViewmodel(application: Application) : AndroidViewModel(application)
{


    private val cardarray = MutableLiveData<java.util.ArrayList<SavedCardsModel>>()
    private val paymentmethodarray = MutableLiveData<java.util.ArrayList<PaymentMethodsModel>>()
    private val onpaymentinfo = MutableLiveData<Boolean>()
    private val onsavedcardinfo = MutableLiveData<Int>()


    fun getrecentcardlist(): MutableLiveData<ArrayList<SavedCardsModel>>
    {
        return cardarray
    }
    fun getsavedcarddisplay(): MutableLiveData<Int>
    {
        return onsavedcardinfo
    }
    fun getpaymentmethod(): MutableLiveData<ArrayList<PaymentMethodsModel>>
    {
        return paymentmethodarray
    }
    fun getpaymentinfo(): MutableLiveData<Boolean>
    {
        return onpaymentinfo
    }

    var dashboarddata: paymentpagepagerepostiatry
    init
    {
         dashboarddata = paymentpagepagerepostiatry( object : PaymentPageListener
         {
             override fun onPaymentMethodsModel(mutableLiveData: java.util.ArrayList<PaymentMethodsModel>) {
                 paymentmethodarray.value = mutableLiveData
             }

             override fun onSavedCardsModel(mutableLiveData: java.util.ArrayList<SavedCardsModel>) {
                 cardarray.value = mutableLiveData
             }

             override fun onisCardAvail(isCardAvail: Boolean) {
                 TODO("Not yet implemented")
             }

             override fun onPaymentinfovisible(paymentinfo: Boolean) {
                 onpaymentinfo.value = paymentinfo
             }

             override fun onSavedCardInfo(savedcardinfo: Int) {
                 onsavedcardinfo.value =savedcardinfo
             }

         })
    }



    fun paymentTypeApiParser(mContext: Activity,response:String)
    {
        dashboarddata.paymentTypeApiParser(mContext,response)
    }


}