package com.mauto.chd.backgroundservices


import android.app.IntentService
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.speech.tts.TextToSpeech
import com.mauto.chd.R
import com.mauto.chd.SessionManagerPackage.LanguageSessionManager
import java.util.*


class nearningcustomerlocation : IntentService("ApiHit"), TextToSpeech.OnInitListener
{

    private var tts: TextToSpeech? = null
    private lateinit var mediaPlayer: MediaPlayer

    override fun onBind(intent: Intent): IBinder?
    {
        throw UnsupportedOperationException("Not yet implemented")
    }
    override fun onHandleIntent(intent: Intent?)
    {
        var langmSessionManager = LanguageSessionManager(applicationContext!!)
        if(langmSessionManager!!.getlanguageoption().equals("1"))
            initizemediaplayer()
        else
            tts = TextToSpeech(applicationContext, this)

    }

    override fun onInit(status: Int)
    {
        if (status == TextToSpeech.SUCCESS)
        {
            val result = tts!!.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
            {
            }
            else
                tts!!.speak(getString(R.string.voiceofpickup), TextToSpeech.QUEUE_FLUSH, null,"")
        }
    }

    fun initizemediaplayer()
    {
//        mediaPlayer = MediaPlayer.create(applicationContext,R.raw.peakyblinders)
//        mediaPlayer.start()
//        val MAX_VOLUME = 100
//        val volume = (1 - Math.log((MAX_VOLUME - 80).toDouble()) / Math.log(MAX_VOLUME.toDouble())).toFloat()
//        var audioManager: AudioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager;
//        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 100, 0);
//        try
//        {
//            mediaPlayer.setVolume(volume, volume)
//        }
//        catch (e: Exception)
//        {
//        }
    }

}