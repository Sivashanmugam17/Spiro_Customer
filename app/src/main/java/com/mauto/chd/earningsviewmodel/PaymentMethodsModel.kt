package com.mauto.chd.earningsviewmodel

import java.io.Serializable

class PaymentMethodsModel(val
                          paymentMethodName: String? = "",
                          val paymentMethodCode: String? = "",
                          var paymentMethodSelected: String? = "",
                          var card_number:String?="",
                          var card_id:String?="",
                          var exp_month:String?="",
                          var exp_year:String?="",
                          var card_type:String?="",
                          var type:String?="",
                          var code:String?=""





) : Serializable