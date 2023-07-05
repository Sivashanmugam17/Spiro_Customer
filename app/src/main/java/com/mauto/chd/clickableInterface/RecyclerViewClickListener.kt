package com.mauto.chd.clickableInterface

import android.view.View
import com.mauto.chd.view_model_with_repositary_main.EarningModel


interface RecyclerViewClickListener {
    fun onRecyclerViewItemClick(view: View, earning: EarningModel)
}