package com.hiy.monbie.core.ext

import android.view.View
import android.widget.TextView
import com.hiy.monbie.core.R


fun View.setToolbarTitle( title: String) {
    findViewById<TextView>(R.id.title_tv)?.apply {
        this.text = title
    }
}

fun View.setToolbarRightText(text: String, listener: View.OnClickListener?) {
    findViewById<TextView>(R.id.right_tv)?.apply {
        this.text = text

        setOnClickListener {
            listener?.onClick(it)
        }
    }
}