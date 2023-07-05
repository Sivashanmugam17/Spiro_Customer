package com.mauto.chd.custom_text_view_package

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.Button


class Buttonnormal : Button
{
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)
    {
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    {
        init()
    }
    constructor(context: Context) : super(context)
    {
        init()
    }
    fun init()
    {
        val tf = Typeface.createFromAsset(context.assets, "font/sfuidisplaybold")
        typeface = tf
    }
}
