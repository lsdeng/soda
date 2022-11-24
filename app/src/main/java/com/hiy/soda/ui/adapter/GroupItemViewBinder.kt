package com.hiy.soda.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.drakeet.multitype.MultiTypeAdapter
import com.hiy.soda.R
import com.hiy.soda.bean.bo.GroupBo
import com.hiy.soda.bean.bo.OptionBo

class GroupItemViewBinder : ItemViewBinder<GroupBo, BaseViewHolder>() {
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): BaseViewHolder {
        val view = inflater.inflate(R.layout.list_item_group, parent, false)
        return BaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, item: GroupBo) {
        val rv = holder.itemView.findViewById<RecyclerView>(R.id.rv1)
        rv.layoutManager = LinearLayoutManager(holder.itemView.context, LinearLayoutManager.VERTICAL, holder.adapterPosition % 2 == 0)
        val list = listOf<Any>(
            "111",
            OptionBo("11", "11"),
            OptionBo("2", "2"),
            OptionBo("3", "3")
        )
        val adapter = MultiTypeAdapter(list)
        adapter.register(OptionBo::class.java, OptionItemViewBinder())
        adapter.register(String::class.java, StringItemViewBinder())
        rv.adapter = adapter
        adapter.notifyDataSetChanged()
        Log.d("lsd", "${holder.bindingAdapterPosition}-${adapter.itemCount}")
    }
}