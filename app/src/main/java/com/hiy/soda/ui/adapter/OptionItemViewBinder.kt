package com.hiy.soda.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.drakeet.multitype.ItemViewBinder
import com.hiy.soda.R
import com.hiy.soda.bean.bo.OptionBo
import com.hiy.soda.ui.RvAc

class OptionItemViewBinder : ItemViewBinder<OptionBo, BaseViewHolder>() {
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): BaseViewHolder {
        val view = inflater.inflate(R.layout.list_item_option, parent, false)
        return BaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, item: OptionBo) {
        holder.itemView.findViewById<TextView>(R.id.name_tv).apply {
            this.text = item.name
        }

        holder.itemView.setOnClickListener {
           if(item.key != "rv") {
               return@setOnClickListener
           }
            val intent = Intent(it.context, RvAc::class.java)
            it.context.startActivity(intent)
        }

    }
}