package com.hiy.soda.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.MultiTypeAdapter
import com.hiy.soda.R
import com.hiy.soda.bean.bo.GroupBo
import com.hiy.soda.ui.adapter.GroupItemViewBinder

class RvAc : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rv)

        val list = mutableListOf<Any>()
        list.add(GroupBo("", ""))
        list.add(GroupBo("", ""))
        list.add(GroupBo("", ""))
        list.add(GroupBo("", ""))
        list.add(GroupBo("", ""))
        list.add(GroupBo("", ""))
        val rv = findViewById<RecyclerView>(R.id.rv)
        val adapter = MultiTypeAdapter(list)
        adapter.register(GroupBo::class.java, GroupItemViewBinder())

        rv.adapter = adapter

    }
}