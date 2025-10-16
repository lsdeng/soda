package com.hiy.soda.plugin

import android.app.Application
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

// 在 app 模块的 Application 类中
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // 初始化所有插件
        PluginManager.onCreate()
    }
}


// 在 app 模块中
@Plugin("LoggerPlugin")
class LoggerPlugin : IPlugin {
    override fun onCreate() {
        println("LoggerPlugin is created!")
    }
}

@Plugin("AnalyticsPlugin")
class AnalyticsPlugin : IPlugin {
    override fun onCreate() {
        println("AnalyticsPlugin is created!")
    }
}