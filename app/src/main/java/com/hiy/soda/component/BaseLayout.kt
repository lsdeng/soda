package com.hiy.soda.component

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.FrameLayout
import com.hiy.monbie.core.HiyHelper

/**
 * auther: liusaideng
 * created on :  2022/11/30 11:44 上午
 * desc:
 */
class BaseLayout : FrameLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Log.d(HiyHelper.tag_view_touch, "进入[viewGroup]-dispatchTouchEvent")
        return super.dispatchTouchEvent(ev).apply {
            Log.d(HiyHelper.tag_view_touch, "[viewGroup]-dispatchTouchEvent-${this}")
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        Log.d(HiyHelper.tag_view_touch, "进入[viewGroup]-onInterceptTouchEvent")
        return super.onInterceptTouchEvent(ev).apply {
            Log.d(HiyHelper.tag_view_touch, "[viewGroup]-onInterceptTouchEvent-${this}")
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d(HiyHelper.tag_view_touch, "进入[viewGroup]-onTouchEvent")
        return super.onTouchEvent(event).apply {
            Log.d(HiyHelper.tag_view_touch, "[viewGroup]-onTouchEvent-${this}")
        }
    }
}