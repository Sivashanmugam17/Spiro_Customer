package com.mauto.chd.SessionManagerPackage

import android.content.Context
import android.content.SharedPreferences

class LanguageSessionManager(var mContext: Context)
{
    var mSharedPreferenceName = "languagefile"
    var mSharedPreferenceMode = 0
    private var mSharedPreference: SharedPreferences = mContext.getSharedPreferences(mSharedPreferenceName, mSharedPreferenceMode)
    private var mEditor = mSharedPreference.edit()

    val languagechoosen = "languagechoosen"



    fun setLanguageChoosen(value:String)
    {
        mEditor.putString(languagechoosen, value)
        mEditor.apply()
    }

    fun getlanguageoption(): String {
        return mSharedPreference.getString(languagechoosen, "2")!!
    }


}
