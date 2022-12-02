package com.hiy.soda

import android.app.Application
import android.content.Context
import androidx.startup.AppInitializer
import com.hiy.soda.helper.startup.WorkManagerInitializer
import com.hiy.soda.helper.startup.WorkManagerInitializer1

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


        AppInitializer.getInstance(applicationContext).initializeComponent(WorkManagerInitializer::class.java)
        AppInitializer.getInstance(applicationContext).initializeComponent(WorkManagerInitializer1::class.java)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }
}