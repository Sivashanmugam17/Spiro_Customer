package com.mauto.chd.view_model_tracking_page

class CancellationReasonDataModel
{
    var id: String? = null
    var reason: String? = null
    var cancel_charge: String? = null
    var currency_code: String? = null
    var cancellation_amount: String? = null


    constructor(id: String, reason: String, cancel_charge: String, currency_code: String, cancellation_amount: String)
    {
        this.id = id
        this.reason = reason

        this.cancel_charge = cancel_charge
        this.currency_code = currency_code
        this.cancellation_amount = cancellation_amount

    }
}