package com.mauto.chd.Modal

class TransactionHistoryModelCustomer {
    var gateway: String? = null
    var txn_date: String? = null
    var txn_amount: String? = null
    var currency_code: String? = null
    var txn_id: String? = null




    constructor(gateway: String, txn_date: String, txn_amount: String,currency_code: String,txn_id: String) {
        this.gateway = gateway
        this.txn_date = txn_date
        this.txn_amount = txn_amount
        this.currency_code=currency_code
        this.txn_id=txn_id

    }
}