package com.mauto.chd.earningsviewmodel

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class PaymentMethodsRealmModel : RealmObject() {
    @PrimaryKey
    var payment_method_id: Int? = null
    var payment_method_code: String = ""
    var payment_method_type: String = ""
    var payment_is_default: String = ""
}