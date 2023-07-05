package com.mauto.chd.earningsviewmodel

import android.app.Activity
import android.util.Log
import com.mauto.chd.data.SavedCardsDbHelper
import org.json.JSONObject

import java.util.*


class paymentpagepagerepostiatry(private val listener: PaymentPageListener)
{

     fun paymentTypeApiParser(mContext: Activity,response:String)
    {
        var realmController: PaymentMethodsRealmController? = null
        realmController = PaymentMethodsRealmController()
        var savedCardsDbHelper: SavedCardsDbHelper =SavedCardsDbHelper(mContext)

        var paymentMethodArray: ArrayList<PaymentMethodsModel> = ArrayList<PaymentMethodsModel>()
        var saved_cards_array: ArrayList<SavedCardsModel> = ArrayList<SavedCardsModel>()
        var isCardAvail: Boolean = false

         var card_payment_method_type: String = ""
         var card_payment_method_code: String = ""
         var card_payment_method_is_default: String = ""


        val response_json_object = JSONObject(response)
        Log.d("mcheckings202",response)
        try
        {
            val status = response_json_object.getString("status")
            if (status.equals("1"))
            {
                val response_object = response_json_object.getJSONObject("response")
                val payment_method_array = response_object.getJSONArray("payment_method")
                paymentMethodArray?.clear()
                saved_cards_array?.clear()
                isCardAvail = false
                savedCardsDbHelper.clearTable()
                for (i in 0 until payment_method_array.length())
                {
                    val payment_method_type = payment_method_array.getJSONObject(i).getString("type")
                    val payment_method_code = payment_method_array.getJSONObject(i).getString("code")
                    val payment_method_is_default = payment_method_array.getJSONObject(i).getString("is_default")
                    if (payment_method_code.equals("card")) {
                        isCardAvail = true
                        card_payment_method_type = payment_method_type
                        card_payment_method_code = payment_method_code
                        card_payment_method_is_default = payment_method_is_default

                        val card_holder_name = payment_method_array.getJSONObject(i).getString("name")
                        val card_number = payment_method_array.getJSONObject(i).getString("card_number")
                        val card_type = payment_method_array.getJSONObject(i).getString("card_type")
                        val card_id = payment_method_array.getJSONObject(i).getString("card_id")
                        val exp_month = payment_method_array.getJSONObject(i).getString("exp_month")
                        val exp_year = payment_method_array.getJSONObject(i).getString("exp_year")
                        val is_default_card = payment_method_array.getJSONObject(i).getString("is_default_card")
                        val image = ""
                        saved_cards_array?.add(SavedCardsModel(card_payment_method_type, card_payment_method_code, card_payment_method_is_default, card_holder_name, card_number, card_type, card_id, exp_month, exp_year, is_default_card, image))

                        savedCardsDbHelper.insertSavedCards(SavedCardsModel(card_payment_method_type, card_payment_method_code, card_payment_method_is_default, card_holder_name, card_number, card_type, card_id, exp_month, exp_year, is_default_card, image))

                        paymentMethodArray?.add(PaymentMethodsModel(payment_method_type, payment_method_code, payment_method_is_default,card_number,card_id,exp_month,exp_year,card_type))

                        val payment_method_array = response_object.getString("card_payment")
                        if (payment_method_array.equals("1"))
                        {
                            if (listener != null)
                            {
                                listener!!.onSavedCardInfo(2)
                            }
                        }
                        else  if (payment_method_array.equals("1"))
                        {
                            if (listener != null)
                            {
                                listener!!.onSavedCardInfo(3)
                            }
                        }
                    }
                    else
                    {
                        paymentMethodArray?.add(PaymentMethodsModel(payment_method_type, payment_method_code, payment_method_is_default))
                        savedCardsDbHelper.insertSavedCards(SavedCardsModel(card_payment_method_type, card_payment_method_code, card_payment_method_is_default, "", "", "", "", "", "", "", ""))
                    }
                    realmController?.addPaymentMethods(i, payment_method_code, payment_method_type, payment_method_is_default)
                }


                if (listener != null)
                    listener!!.onSavedCardsModel(saved_cards_array)
                if (listener != null)
                    listener!!.onPaymentMethodsModel(paymentMethodArray)
                if (listener != null)
                    listener!!.onPaymentinfovisible(true)
            }
        } catch (e: java.lang.Exception)
        {
            if (listener != null)
            {
                listener!!.onPaymentinfovisible(false)
            }
        }
    }

}