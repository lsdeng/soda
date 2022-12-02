package com.hiy.soda.component

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.hiy.monbie.core.HiyHelper

/**
 * auther: liusaideng
 * created on :  2022/11/30 11:43 上午
 * desc:
 */
class BaseView : View {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d(HiyHelper.tag_view_touch, "view -> onTouchEvent,${event?.action}")
        if (event?.action == MotionEvent.ACTION_DOWN) {
            return true
        }
        return super.onTouchEvent(event).apply {
            Log.d(HiyHelper.tag_view_touch, "view 触发 $this")
        }
    }
}