//package com.cabilyhandyforalldinedoo.chd.commonutils
//
//import android.annotation.SuppressLint
//import android.content.Context
//import android.graphics.Bitmap
//import android.graphics.Canvas
//import android.graphics.Color
//import android.transition.ChangeBounds
//import android.transition.TransitionManager
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.view.animation.LinearInterpolator
//import androidx.constraintlayout.widget.ConstraintSet
//import cabily.handyforall.dinedoo.R
//import kotlinx.android.synthetic.main.book_now_drop_marker_adjust.view.*
//
//class BookNowDropIconGen(context: Context, from_page: Int) {
//    lateinit var mContext: Context
//    lateinit var mContainer: ViewGroup
//    lateinit var mContainerDrop: ViewGroup
//
//    lateinit var mRotationalLayout: View
//    private var mRotation: Int = 0
//    var constraint_set = ConstraintSet()
//
//    init {
//        mContext = context
//        if (from_page == 2) {
////            mContainerDrop = LayoutInflater.from(mContext).inflate(R.layout.book_now_drop_marker_adjust, null) as ViewGroup
////            mRotationalLayout = mContainer.getChildAt(0)
//        }
//    }
//
//    fun clearContext() {
//        if (mContext !== null) {
//            mContext == null
//        }
//    }
//
//    @SuppressLint("ResourceAsColor")
//    fun makeIcon(address: String, flag: Int): Bitmap {
//        if (flag == 2) {
//            mContainerDrop.drop_marker_adjust_address_bg_view.setBackgroundResource(R.drawable.map_right_curve_drop)
//            mContainerDrop.drop_marker_iv.setImageResource(R.drawable.ic_drop_at)
//            mContainerDrop.drop_address_tv.setText(address)
////            mContainerDrop.drop_address_tv.setTextColor(R.color.white)
//        }
//        return makeIconDrop()
//    }
//
//    @SuppressLint("ResourceAsColor")
//    fun adjustAddressview(address: String, flag: Int, isMove: Boolean): Bitmap {
//        if (flag == 2) {
//            if (isMove) {
//                constraint_set = ConstraintSet()
//                constraint_set.clone(mContainerDrop.constraint_layout_drop)
//                constraint_set.clear(R.id.drop_marker_adjust_address_bg_view, ConstraintSet.START)
//                constraint_set.connect(R.id.drop_marker_adjust_address_bg_view, ConstraintSet.END, R.id.drop_marker_iv, ConstraintSet.START)
//                constraint_set.setMargin(R.id.drop_marker_adjust_address_bg_view, ConstraintSet.END, 15)
//                val transition = ChangeBounds()
//                transition.interpolator = LinearInterpolator()
//                transition.duration = 400
//                TransitionManager.beginDelayedTransition(mContainerDrop.constraint_layout_drop, transition)
//                constraint_set.applyTo(mContainerDrop.constraint_layout_drop)
//
//
//            } else if (!isMove) {
//                constraint_set = ConstraintSet()
//                constraint_set.clone(mContainerDrop.constraint_layout_drop)
//                constraint_set.clear(R.id.drop_marker_adjust_address_bg_view, ConstraintSet.END)
//                constraint_set.connect(R.id.drop_marker_adjust_address_bg_view, ConstraintSet.START, R.id.drop_marker_iv, ConstraintSet.END)
//                constraint_set.setMargin(R.id.drop_marker_adjust_address_bg_view, ConstraintSet.START, 15)
//                val transition = ChangeBounds()
//                transition.interpolator = LinearInterpolator()
//                transition.duration = 400
//                TransitionManager.beginDelayedTransition(mContainerDrop.constraint_layout_drop, transition)
//                constraint_set.applyTo(mContainerDrop.constraint_layout_drop)
//
//            }
//
//        }
//
//        if (flag == 2) {
//            mContainerDrop.drop_marker_adjust_address_bg_view.setBackgroundResource(R.drawable.map_right_curve_drop)
//            mContainerDrop.drop_marker_iv.setImageResource(R.drawable.ic_drop_at)
//            mContainerDrop.drop_address_tv.setText(address)
////            mContainerDrop.drop_address_tv.setTextColor(R.color.white)
//        }
//        return makeIconDrop()
//    }
//
//    fun makeIconDrop(): Bitmap {
//        val measureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
//        mContainerDrop.measure(measureSpec, measureSpec)
//
//        var measuredWidth = mContainerDrop.measuredWidth
//        var measuredHeight = mContainerDrop.measuredHeight
//
//        mContainerDrop.layout(0, 0, measuredWidth, measuredHeight)
//
//        if (mRotation == 1 || mRotation == 3) {
//            measuredHeight = mContainerDrop.measuredWidth
//            measuredWidth = mContainerDrop.measuredHeight
//        }
//
//        val r = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888)
//        r.eraseColor(Color.TRANSPARENT)
//
//        val canvas = Canvas(r)
//
//        when (mRotation) {
//            0 -> {
//            }
//            1 -> {
//                canvas.translate(measuredWidth.toFloat(), 0f)
//                canvas.rotate(90f)
//            }
//            2 -> canvas.rotate(180f, (measuredWidth / 2).toFloat(), (measuredHeight / 2).toFloat())
//            3 -> {
//                canvas.translate(0f, measuredHeight.toFloat())
//                canvas.rotate(270f)
//            }
//        }// do nothing
//        mContainerDrop.draw(canvas)
//        return r
//    }
//
//}