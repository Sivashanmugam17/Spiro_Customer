package com.mauto.chd.view_model_register_module


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mauto.chd.SessionManagerPackage.SessionManager
import java.io.ByteArrayOutputStream
import java.util.*


class RegisterpageoneViewModel : ViewModel()
{
    private val countrycodestringemit = MutableLiveData<String>()
    private val countrynamestringemit = MutableLiveData<String>()
    private val bitmapimage = MutableLiveData<Bitmap>()
    private var mSessionManager: SessionManager? = null
    private val getcountrycodewithshortname = MutableLiveData<String>()

    fun flagbase64stringobservervalue(): MutableLiveData<Bitmap>
    {
        return bitmapimage
    }
    fun countrycodeobservervalue(): MutableLiveData<String>
    {
        return countrycodestringemit
    }
    fun countrynameobservervalue(): MutableLiveData<String>
    {
        return countrynamestringemit
    }
    fun defaultflagprocessor(mContext: Context,defaultflagcode:String)
    {
        val codeNameArray = defaultflagcode.split(",")
        val stringBuilder = StringBuilder()
        stringBuilder.append(getCountryZipCode(codeNameArray[1].trim()))
        val codes = codeNameArray[0].trim()
        val imageName = codeNameArray[1].trim()
        val imageBuilder = StringBuilder()
        imageBuilder.append("flags/flag_").append(imageName.toLowerCase()).append(".png")
        val inputStream = mContext.assets.open(imageBuilder.toString())
        val bitmap = BitmapFactory.decodeStream(inputStream)
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b = baos.toByteArray()
        val flagbase64string = Base64.encodeToString(b, Base64.DEFAULT)
        mSessionManager = SessionManager(mContext)
        mSessionManager!!.setFlagDetails(flagbase64string,codes,imageName)
        bitmapimage.value=StringToBitMap(flagbase64string)
        countrycodestringemit.value=codes
        countrynamestringemit.value=imageName
    }
    fun StringToBitMap(encodedString: String): Bitmap?
    {
        try
        {
            val encodeByte = Base64.decode(encodedString, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
        }
        catch (e: Exception)
        {
            e.message
            return null
        }
    }
    fun countrycodewithshortnameobservervalue(): MutableLiveData<String>
    {
        return getcountrycodewithshortname
    }
    fun getflagdetailsfromsession(mContext: Context)
    {
        mSessionManager = SessionManager(mContext)
        var countrycode= mSessionManager?.getFlagDetails()!![mSessionManager!!.countrycodesaved]!!
        var countrynamesaved= mSessionManager?.getFlagDetails()!![mSessionManager!!.countrynamesaved]!!
        getcountrycodewithshortname.value = countrycode+","+countrynamesaved
    }
    private fun getCountryZipCode(ssid: String): String
    {
        val loc = Locale("", ssid)
        return loc.displayCountry.trim { it <= ' ' }
    }
    override fun onCleared()
    {
    }
}

