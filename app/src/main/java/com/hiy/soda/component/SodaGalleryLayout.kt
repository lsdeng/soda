package com.hiy.soda.component

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.hiy.soda.R

class SodaGalleryLayout : RelativeLayout {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onFinishInflate() {
        super.onFinishInflate()
        inflate(context, R.layout.layout_image, this)
    }

    fun getNameView(): TextView = findViewById<TextView>(R.id.name_tv)

    fun getContentView(): ImageView = findViewById<ImageView>(R.id.content_iv)
}