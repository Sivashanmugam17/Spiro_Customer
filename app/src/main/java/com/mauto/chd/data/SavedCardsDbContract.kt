package com.mauto.chd.data

import android.provider.BaseColumns

object SavedCardsDbContract {

    /* Inner class that defines the table contents */
    class SavedCardsEntry : BaseColumns {
        companion object {
            val TABLE_NAME      = "savedcaoords"
            val COLUMN_USER_ID  = "id"
            val COLUMN_PAYMENT_METHOD_TYPE    = "payment_method_type"
            val COLUMN_PAYMENT_METHOD_CODE    = "payment_method_code"
            val COLUMN_PAYMENT_METHOD_IS_DEFAULT    = "payment_method_is_default"
            val COLUMN_CARD_HOLDER_NAME    = "card_holder_name"
            val COLUMN_CARD_NUMBER = "card_number"
            val COLUMN_CARD_TYPE      = "card_type"
            val COLUMN_CARD_ID     = "card_id"
            val COLUMN_CARD_EXP_MONTH     = "card_exp_month"
            val COLUMN_CARD_EXP_YEAR    = "card_exp_year"
            val COLUMN_CARD_SELECTED     = "card_selected"
            val COLUMN_CARD_IMAGE     = "card_image"

        }
    }
}