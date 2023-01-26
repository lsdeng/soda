package com.hiy.soda.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hiy.soda.R
import com.hiy.soda.ui.main.MainAc

class SplashAc : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_splash)

        startActivity(Intent(this@SplashAc, MainAc::class.java))
    }
}