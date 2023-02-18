package com.hiy.monbie.core

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment

class WrapperActivity : BaseActivity() {

    companion object {
        fun nav(ac: Activity, fgClz : Class<out Fragment>, bundle: Bundle?) {
            Intent(ac, WrapperActivity::class.java).apply {
                bundle?.let {
                    this.putExtras(it)
                }
                this.putExtra("class_name", fgClz)
                ac.startActivity(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_wrapper)

        supportFragmentManager.beginTransaction()
            .add(R.id.fg_container, getFgClass(), intent.extras)
            .commit()
    }

    fun getFgClass() : Class<out  Fragment> {
        return intent.getSerializableExtra("class_name")!! as Class<out Fragment>
    }






}