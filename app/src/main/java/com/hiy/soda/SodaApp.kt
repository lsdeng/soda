package com.hiy.soda

import android.app.Application
import android.content.Context

class SodaApp : Application() {

    companion object Instance {
        lateinit var application: Application

        fun getContext() : Context{
            return application.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        application = this@SodaApp
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }
}