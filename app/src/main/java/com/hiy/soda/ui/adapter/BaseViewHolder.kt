package com.hiy.soda.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun <T : View> findById(id: Int): T {
        return itemView.findViewById<T>(id)
    }
}