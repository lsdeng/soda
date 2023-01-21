package com.hiy.soda.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.drakeet.multitype.ItemViewBinder
import com.hiy.soda.R
import com.hiy.soda.bean.bo.GridBo

/**
 * auther: liusaideng
 * created on :  2023/1/10 10:55 上午
 * desc:
 */
class GridItemViewBinder : ItemViewBinder<GridBo, BaseViewHolder>() {
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): BaseViewHolder {
        val view = inflater.inflate(R.layout.list_item_grid, parent, false)
        return BaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, item: GridBo) {
        val gridTv = holder.itemView.findViewById<TextView>(R.id.grid_tv)
        gridTv.text = item.title
    }

}