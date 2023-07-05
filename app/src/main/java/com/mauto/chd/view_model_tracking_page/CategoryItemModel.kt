package com.mauto.chd.view_model_tracking_page

class CategoryItemModel(category_id: String, category_name: String, category_seat_capacity: String, category_image: String, category_estimated_time: String, currency_symbol: String, category_estimated_price: String, category_discounted_estimated_price: String, category_is_pool_option: String, nearest_driver_arriving_time: String, cab_avail: String,is_selected:String) {
    var mCategoryId: String
    var mCategoryName: String
    var mCategorySeatCapacity: String
    var mCategoryImage: String
    var mCategoryEstimatedTime: String
    var mCurrencySymbol: String
    var mCategoryEstimatedPrice: String
    var mCategoryDiscountedEstimatedPrice:String
    var mCategoryIsPoolOption: String
    var mNearestDriverArrivingTime: String
    var mCabAvail: String
    var isselected: String


    init {
        mCategoryId = category_id
        mCategoryName = category_name
        mCategorySeatCapacity = category_seat_capacity
        mCategoryImage = category_image
        isselected = is_selected

        mCategoryEstimatedTime = category_estimated_time
        mCurrencySymbol = currency_symbol
        mCategoryEstimatedPrice = category_estimated_price
        mCategoryDiscountedEstimatedPrice=category_discounted_estimated_price
        mCategoryIsPoolOption = category_is_pool_option
        mNearestDriverArrivingTime = nearest_driver_arriving_time
        mCabAvail = cab_avail

    }

    fun getCategoryId(): String {
        return mCategoryId
    }

    fun getisselected(): String {
        return isselected
    }

    fun getCategoryName(): String {
        return mCategoryName
    }

    fun getCategorySeatCapacity(): String {
        return mCategorySeatCapacity
    }

    fun getCategoryImage(): String {
        return mCategoryImage
    }

    fun getCategoryEstimatedTime(): String {
        return mCategoryEstimatedTime
    }

    fun getCurrencySymbol(): String {
        return mCurrencySymbol
    }

    fun getCategoryEstimatedPrice(): String {
        return mCategoryEstimatedPrice
    }

    fun getCategoryIsPoolOption(): String {
        return mCategoryIsPoolOption
    }

    fun getNearestDriverArrivingTime(): String {
        return mNearestDriverArrivingTime
    }

    fun getCabAvail(): String {
        return mCabAvail
    }

    fun getCategoryDiscountedEstimatedPrice(): String {
        return mCategoryDiscountedEstimatedPrice
    }

}