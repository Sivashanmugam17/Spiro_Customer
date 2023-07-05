package org.andcoe.floatingwidget
//
//import android.content.Context
//import android.content.Intent
//import android.graphics.PixelFormat
//import android.util.AttributeSet
//import android.view.MotionEvent
//import android.view.View
//import android.view.WindowManager
//import androidx.constraintlayout.widget.ConstraintLayout
//import cabily.handyforall.dinedoo.R
////import com.cabilyhandyforalldinedoo.chd.ui.MainPage.requestmianscreen
//import android.os.Build.VERSION_CODES.O
//import android.os.Build.VERSION.SDK_INT
//import android.icu.lang.UCharacter.GraphemeClusterBreak.T
//import android.os.Build
//import android.icu.lang.UCharacter.GraphemeClusterBreak.T
//import android.icu.lang.UCharacter.GraphemeClusterBreak.T
//import android.util.DisplayMetrics
//
//
//class FloatingWidgetView : ConstraintLayout, View.OnTouchListener {
//
//    constructor(context: Context) : super(context)
//    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
//    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
//    private var layoutParams:WindowManager.LayoutParams ?= null
//    private var x: Int = -250
//    private var y: Int = 0
//    private var touchX: Float = 0f
//    private var touchY: Float = 0f
//    private var clickStartTimer: Long = 0
//    private val windowManager: WindowManager
//
//    init
//    {
//        View.inflate(context, R.layout.floating_widget_layout, this)
//        setOnTouchListener(this)
//        val LAYOUT_FLAG: Int
//        if (SDK_INT >= O)
//            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
//        else
//            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE
//
//         layoutParams = WindowManager.LayoutParams(
//                WindowManager.LayoutParams.WRAP_CONTENT,
//                WindowManager.LayoutParams.WRAP_CONTENT,
//                 LAYOUT_FLAG,
//                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
//                PixelFormat.TRANSLUCENT
//        )
//
//        windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
//        val dm = DisplayMetrics()
//        windowManager.getDefaultDisplay().getMetrics(dm)
//         x = -dm.widthPixels
//
//        layoutParams!!.x = x
//        layoutParams!!.y = y
//
//
//        windowManager.addView(this, layoutParams)
//    }
//
//    companion object {
//        private const val CLICK_DELTA = 200
//    }
//
//    override fun onTouch(v: View, event: MotionEvent): Boolean {
//        when (event.action) {
//            MotionEvent.ACTION_DOWN -> {
//                clickStartTimer = System.currentTimeMillis()
//
//                x = layoutParams!!.x
//                y = layoutParams!!.y
//
//                touchX = event.rawX
//                touchY = event.rawY
//            }
//            MotionEvent.ACTION_UP -> {
//                if (System.currentTimeMillis() - clickStartTimer < CLICK_DELTA) {
//                    openapp()
//                }
//            }
//            MotionEvent.ACTION_MOVE -> {
//                layoutParams!!.x = (x + event.rawX - touchX).toInt()
//                layoutParams!!.y = (y + event.rawY - touchY).toInt()
//                windowManager.updateViewLayout(this, layoutParams)
//            }
//        }
//        return true
//    }
//
//    fun openapp()
//    {
////        val intent_otppage = Intent(context, requestmianscreen::class.java)
////        intent_otppage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
////        context.startActivity(intent_otppage)
//    }
//}
