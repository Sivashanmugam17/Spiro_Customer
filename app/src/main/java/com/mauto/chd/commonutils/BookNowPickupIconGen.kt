//package com.cabilyhandyforalldinedoo.chd.commonutils
//
//import android.annotation.SuppressLint
//import android.content.Context
//import android.graphics.Bitmap
//import android.graphics.Canvas
//import android.graphics.Color
//import androidx.constraintlayout.widget.ConstraintSet
//import android.transition.ChangeBounds
//import android.transition.TransitionManager
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.view.animation.LinearInterpolator
//import cabily.handyforall.dinedoo.R
//import kotlinx.android.synthetic.main.book_now_pickup_marker_adjust.view.*
//
//class BookNowPickupIconGen(context: Context, from_page: Int) {
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
//        if (from_page == 1) {
////            mContainer = LayoutInflater.from(mContext).inflate(R.layout.book_now_pickup_marker_adjust, null) as ViewGroup
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
//        if (flag == 1) {
//            mContainer.marker_adjust_address_bg_view.setBackgroundResource(R.drawable.booking_pick_up_marker_address_view_bg)
//            mContainer.pick_up_marker_iv.setImageResource(R.drawable.ic_pick_at)
//            mContainer.pick_up_address_tv.setText(address)
////            mContainer.pick_up_address_tv.setTextColor(R.color.white)
//        }
//        if (flag == 1) {
////            mContainer.arrival_time_tv.setText("--:--" + "\n" + mContext.getString(R.string.driving_arriving_mins_label))
//        }
//        return makeIcon()
//    }
//
//    @SuppressLint("ResourceAsColor")
//    fun adjustAddressview(address: String, flag: Int, isMove: Boolean, arriving_time: String): Bitmap {
//        if (flag == 1) {
//            if (isMove) {
//                constraint_set = ConstraintSet()
//                constraint_set.clone(mContainer.constraint_layout_pickup)
//                constraint_set.clear(R.id.marker_adjust_address_bg_view, ConstraintSet.START)
//                constraint_set.connect(R.id.marker_adjust_address_bg_view, ConstraintSet.END, R.id.pick_up_marker_iv, ConstraintSet.START)
//                constraint_set.setMargin(R.id.marker_adjust_address_bg_view, ConstraintSet.END, 15)
//                val transition = ChangeBounds()
//                transition.interpolator = LinearInterpolator()
//                transition.duration = 400
//                TransitionManager.beginDelayedTransition(mContainer.constraint_layout_pickup, transition)
//                constraint_set.applyTo(mContainer.constraint_layout_pickup)
//
//
//            } else if (!isMove) {
//                constraint_set = ConstraintSet()
//                constraint_set.clone(mContainer.constraint_layout_pickup)
//                constraint_set.clear(R.id.marker_adjust_address_bg_view, ConstraintSet.END)
//                constraint_set.connect(R.id.marker_adjust_address_bg_view, ConstraintSet.START, R.id.pick_up_marker_iv, ConstraintSet.END)
//                constraint_set.setMargin(R.id.marker_adjust_address_bg_view, ConstraintSet.START, 15)
//                val transition = ChangeBounds()
//                transition.interpolator = LinearInterpolator()
//                transition.duration = 400
//                TransitionManager.beginDelayedTransition(mContainer.constraint_layout_pickup, transition)
//                constraint_set.applyTo(mContainer.constraint_layout_pickup)
//
//            }
//
//        }
//        if (flag == 1) {
//            mContainer.marker_adjust_address_bg_view.setBackgroundResource(R.drawable.booking_pick_up_marker_address_view_bg)
//            mContainer.pick_up_marker_iv.setImageResource(R.drawable.ic_pick_at)
//            mContainer.pick_up_address_tv.setText(address)
//        }
//        return makeIcon()
//    }
//
//    fun updateArrivingTime(flag: Int, arriving_time: String, cab_avail: String): Bitmap {
//
//        if (flag == 1) {
//           /* if (cab_avail.equals("Yes")) {
//                if (arriving_time.equals("0")) {
//                    mContainer.arrival_time_tv.setText("1" + "\n " + mContext.getString(R.string.driving_arriving_mins_label))
//                } else {
//                    mContainer.arrival_time_tv.setText(arriving_time + "\n" + mContext.getString(R.string.driving_arriving_mins_label))
//                }
//            } else {
//                mContainer.arrival_time_tv.setText("--:--" + "\n" + mContext.getString(R.string.driving_arriving_mins_label))
//
//            }*/
//        }
//        return makeIcon()
//    }
//
//    fun makeIcon(): Bitmap {
//        val measureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
//        mContainer.measure(measureSpec, measureSpec)
//
//        var measuredWidth = mContainer.measuredWidth
//        var measuredHeight = mContainer.measuredHeight
//
//        mContainer.layout(0, 0, measuredWidth, measuredHeight)
//
//        if (mRotation == 1 || mRotation == 3) {
//            measuredHeight = mContainer.measuredWidth
//            measuredWidth = mContainer.measuredHeight
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
//        mContainer.draw(canvas)
//        return r
//    }
//}