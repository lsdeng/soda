package com.hiy.soda.component

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout

/**
 * auther: liusaideng
 * created on :  2022/11/30 11:44 上午
 * desc:
 */
class BaseRl : RelativeLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
}