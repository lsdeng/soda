package com.hiy.soda.plugin

import android.util.Log
import com.hiy.monbie.core.HiyHelper
import com.hiy.soda.anno.IPlugin
import com.hiy.soda.anno.Plugin

/**
 * auther: liusaideng
 * created on :  2022/12/1 4:39 下午
 * desc:
 */
@Plugin
class SodaPlugin : IPlugin {
    override fun onCreate() {
        Log.d(HiyHelper.tag_plugin, "onCreate")
    }
}

@Plugin
class HomePlugin : IPlugin {
    override fun onCreate() {
        Log.d(HiyHelper.tag_plugin, "onCreate")
    }
}