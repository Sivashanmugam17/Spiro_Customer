package com.mauto.chd.ui.MainPage

import android.app.Activity
import android.os.Bundle
import com.mauto.chd.ui.registeration.LocaleAwareCompatActivity

class cancelreasondriver : LocaleAwareCompatActivity() {
    private lateinit var mContext: Activity

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.driverideaccpted)
        mContext = this@cancelreasondriver

    }
}