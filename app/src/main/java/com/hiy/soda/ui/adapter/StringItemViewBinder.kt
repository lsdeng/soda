package com.hiy.soda.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.drakeet.multitype.ItemViewBinder
import com.hiy.soda.R

class StringItemViewBinder : ItemViewBinder<String, BaseViewHolder>() {
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): BaseViewHolder {
        val view = inflater.inflate(R.layout.list_item_option, parent, false)
        return BaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, item: String) {
        holder.itemView.findViewById<TextView>(R.id.name_tv).apply {
            this.text = item
        }
    }
}