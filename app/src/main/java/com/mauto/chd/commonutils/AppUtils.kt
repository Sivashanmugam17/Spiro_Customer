package com.mauto.chd.commonutils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mauto.chd.R


class AppUtils
{
    companion object
    {
        fun hideKeyboard(context: Activity, view: View)
        {
            val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            if (imm.isAcceptingText())
            {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
            }

        }
        fun showKeyboard(context: Activity, view: View)
        {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInputFromInputMethod(view.windowToken, 0)
        }
        fun commonerrorsheet(context: Activity,heading:String,content:String)
        {
            val bottomSheetDialog =  BottomSheetDialog(context)
            val view = context.getLayoutInflater().inflate(R.layout.errorlisten, null);
            bottomSheetDialog.setContentView(view)

            val title = view.findViewById(R.id.title) as TextView
            val subcontent = view.findViewById(R.id.subcontent) as TextView

            title.setText(heading)
            subcontent.setText(content)
            bottomSheetDialog.show()
        }
        fun wazeormap(context: Activity,heading:String,content:String)
        {
//            val bottomSheetDialog =  BottomSheetDialog(context)
//            val view = context.getLayoutInflater().inflate(R.layout.wazeormapfailed, null);
//            bottomSheetDialog.setContentView(view)
//
//            val title = view.findViewById(R.id.title) as TextView
//            val subcontent = view.findViewById(R.id.subcontent) as TextView
//
//            val close = view.findViewById(R.id.close) as TextView
//            val movetostore = view.findViewById(R.id.movetostore) as TextView
//
//            title.setText(heading)
//            subcontent.setText(content)
//            close.setOnClickListener {
//                bottomSheetDialog.dismiss()
//            }
//            movetostore.setOnClickListener {
//                bottomSheetDialog.dismiss()
//                try {
//                    context.startActivity(Intent(ACTION_VIEW, Uri.parse("market://details?id=com.waze")))
//                } catch (anfe: android.content.ActivityNotFoundException) {
//                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.waze")))
//                }
//
//            }
//            bottomSheetDialog.show()
        }
         fun isNetworkConnected(context: Activity): Boolean
        {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            return cm!!.getActiveNetworkInfo() != null && cm!!.getActiveNetworkInfo()!!.isConnected()
        }
        fun isNetworkConnecteddfromservice(context: Context): Boolean
        {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            return cm!!.getActiveNetworkInfo() != null && cm!!.getActiveNetworkInfo()!!.isConnected()
        }

        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            val activeNetworkInfo = connectivityManager!!.getActiveNetworkInfo()
            return activeNetworkInfo != null && activeNetworkInfo.isConnected()
        }
    }
}
