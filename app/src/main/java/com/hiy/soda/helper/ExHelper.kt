package com.hiy.soda.helper

import android.util.Log

/**
 * auther: liusaideng
 * created on :  2023/1/10 8:53 下午
 * desc:
 */
fun <T> T.logger(): T {
    Log.d(SodaConstant.TAG, "打印：$this")
    return this
}