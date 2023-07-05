package com.mauto.chd.earningsviewmodel


import java.util.ArrayList

interface PaymentPageListener {
    fun onPaymentMethodsModel(mutableLiveData: ArrayList<PaymentMethodsModel>)
    fun onSavedCardsModel(mutableLiveData: ArrayList<SavedCardsModel>)
    fun onisCardAvail(isCardAvail: Boolean)
    fun onPaymentinfovisible(paymentinfo: Boolean)
    fun onSavedCardInfo(savedcardinfo: Int)
}