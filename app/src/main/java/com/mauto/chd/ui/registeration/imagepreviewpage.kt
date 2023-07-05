package com.mauto.chd.ui.registeration


import android.os.Bundle
import com.mauto.chd.R
import kotlinx.android.synthetic.main.imageshow.*


class imagepreviewpage : LocaleAwareCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.imageshow)

//        loader.visibility = View.VISIBLE

//        Glide.with(applicationContext)
//                .load(intent.getStringExtra("imagepath"))
//                .override(1500,1500)
//                .listener(object : RequestListener<Drawable> {
//                    override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
//                        loader.visibility = View.GONE
//                        errormess.visibility = View.VISIBLE
//                        return false
//                    }
//
//                    override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
//                        loader.visibility = View.GONE
//                        previewima.visibility = View.VISIBLE
//                        return false
//                    }
//                })
//                .into(previewima)

        backbutton.setOnClickListener {
            finish()
        }
    }


}
