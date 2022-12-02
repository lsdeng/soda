package com.hiy.soda.helper.startup

import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import com.hiy.monbie.core.HiyHelper

/**
 * auther: liusaideng
 * created on :  2022/11/30 3:53 下午
 * desc:
 */
class WorkManagerInitializer : Initializer<String> {
    override fun create(context: Context): String {
        Log.d(HiyHelper.tag_startup, "11")
        return "lsd"
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        // No dependencies on other libraries.
        return emptyList()
    }
}

class WorkManagerInitializer1 : Initializer<String> {
    override fun create(context: Context): String {
        Log.d(HiyHelper.tag_startup, "222")
        return "lsd2"
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        // No dependencies on other libraries.
        return emptyList()
    }
}