package com.mauto.chd.earningsviewmodel

import io.realm.Realm
import io.realm.RealmResults
import io.realm.exceptions.RealmPrimaryKeyConstraintException


class PaymentMethodsRealmController {

    private var instance: PaymentMethodsRealmController? = null
    private var realm: Realm? = null
    private var paymentMethodsRealmModel: PaymentMethodsRealmModel? = null

    init {
        if (realm == null) {
            realm = Realm.getDefaultInstance()
        }
        paymentMethodsRealmModel = PaymentMethodsRealmModel()
    }

    fun getRealm(): Realm {
        return realm!!
    }

    //find all objects in the PaymentMethodsRealmModel.class
    fun getPaymentMethods(): RealmResults<PaymentMethodsRealmModel> {
        return realm?.where(PaymentMethodsRealmModel::class.java)!!.findAll()
    }

    //query a single item with the given id
    fun getPaymentMethods(id: String): PaymentMethodsRealmModel {
        return realm?.where(PaymentMethodsRealmModel::class.java)!!.equalTo("payment_method_id", id).findFirst()!!
    }

    //check if PaymentMethodsRealmModel.class is empty
    fun hasPaymentMethods(): Boolean {
        val results = realm?.where(PaymentMethodsRealmModel::class.java)!!.findAll()
        return results.size > 0
    }


    fun getSelectedPaymentMethods(): PaymentMethodsRealmModel {

        try {
            return realm?.where(PaymentMethodsRealmModel::class.java)!!.equalTo("payment_is_default", "1").findFirst()!!

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return paymentMethodsRealmModel!!
    }

    fun addPaymentMethods(id: Int, payment_code: String, payment_method_type: String, payment_is_default: String)
    {
        realm?.executeTransaction(Realm.Transaction { realm ->
            try
            {
                val result = realm.where(PaymentMethodsRealmModel::class.java)
                        .equalTo("payment_method_code", payment_code)
                        .findAll()
                if(result!!.size == 0)
                {
                    val paymentMethodsRealmModel = realm.createObject(PaymentMethodsRealmModel::class.java, id)
                    paymentMethodsRealmModel?.payment_method_code = payment_code
                    paymentMethodsRealmModel?.payment_method_type = payment_method_type
                    paymentMethodsRealmModel?.payment_is_default = payment_is_default
                    realm.copyToRealm(paymentMethodsRealmModel)
                }
            }
            catch (e: RealmPrimaryKeyConstraintException)
            {
                println("addPaymentMethods expection" + e)
            }
        })
    }

    fun updatePaymentMethods(payment_code: String) {

        realm?.executeTransaction(Realm.Transaction { realm ->
            try {
                val result = realm.where(PaymentMethodsRealmModel::class.java)
                        .equalTo("payment_method_code", payment_code)
                        .findFirst()
                result?.payment_is_default = "1"
                realm.copyToRealmOrUpdate(result!!)
            } catch (e: RealmPrimaryKeyConstraintException) {
                println("updatePaymentMethods expection" + e)

            }


        })
    }

    fun clearPaymentIsSelected() {
        try {
            realm?.executeTransaction(Realm.Transaction { realm ->
                val result = realm.where(PaymentMethodsRealmModel::class.java)
                        .findAll()
                for (r in 0 until result!!.size) {
                    result.get(r)?.payment_is_default = "0"
                    realm.copyToRealmOrUpdate(result)
                }
            })
        } catch (e: RealmPrimaryKeyConstraintException) {
            println("clearPaymentIsSelected expection" + e)
        }
    }

    //Refresh the realm istance
    fun refresh() {
        realm?.refresh()
    }

    //close realm
    fun close() {
/*        try {
            realm?.executeTransaction(Realm.Transaction { realm ->
                if (!realm.isClosed)
                    realm.close()
            })
        } catch (e: RealmPrimaryKeyConstraintException) {

        }*/
    }

    //clear all objects from PaymentMethodsRealmModel.class
    //clear all objects from PaymentMethodsRealmModel.class
    fun clearAll() {
        try {
            realm?.executeTransaction(Realm.Transaction { realm ->
                realm.delete(PaymentMethodsRealmModel::class.java)
            })
        } catch (e: RealmPrimaryKeyConstraintException) {
        }
    }

    /* //query example
     fun queryedBooks(): RealmResults<PaymentMethodsRealmModel> {

         return realm.where(PaymentMethodsRealmModel::class.java)
                 .contains("author", "Author 0")
                 .or()
                 .contains("title", "Realm")
                 .findAll()

     }*/
}