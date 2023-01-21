package com.hiy.soda.helper

import android.util.Log
import android.view.View

/**
 * auther: liusaideng
 * created on :  2023/1/10 8:53 下午
 * desc:
 */
fun <T> T.logger(): T {
    Log.d("soda-core", "打印：$this")
    return this
}

fun View.visible(isVisible: Boolean, isGone: Boolean = true) {
    this.visibility = if (isVisible) {
        View.VISIBLE
    } else if (isGone) {
        View.GONE
    } else {
        View.INVISIBLE
    }
}