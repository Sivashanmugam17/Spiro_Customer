package com.mauto.chd.earningsviewmodel

import java.io.Serializable

class SavedCardsModel(var paymentMethodName: String? = "",
                      var paymentMethodCode: String? = "",
                      var paymentMethodSelected: String? = "",
                      var cardHolderName: String? = "",
                      var cardNumber: String? = "",
                      var cardType: String? = "",
                      var cardId: String? = "",
                      var cardExpMonth: String? = "",
                      var cardExpYear: String? = "",
                      var cardSelected: String? = "",
                      var cardImage: String? = "",
                      var view_type: String? = "") : Serializable {
/*    var paymentMethodName: String? = null
    var paymentMethodCode: String? = null
    var paymentMethodSelected: String? = null

    fun cardPaymentInfo(paymentMethodName: String, paymentMethodCode: String, paymentMethodSelected: String) {
        this.paymentMethodName = paymentMethodName
        this.paymentMethodCode = paymentMethodCode
        this.paymentMethodSelected = paymentMethodSelected

    }*/

}