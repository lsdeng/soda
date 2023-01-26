package com.hiy.soda.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.drakeet.multitype.ItemViewBinder
import com.hiy.soda.R
import com.hiy.soda.bean.dto.Goods
import com.hiy.soda.helper.DateHelper
import java.io.File

class GoodsItemViewBinder : ItemViewBinder<Goods, BaseViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): BaseViewHolder {
        return BaseViewHolder(inflater.inflate(R.layout.list_item_goods, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder, item: Goods) {
        val avatarIv: ImageView = holder.itemView.findViewById(R.id.avatar_iv)
        item.path?.let {
            Glide.with(holder.itemView.context).asBitmap().load(File(it).toUri()).into(BitmapImageViewTarget(avatarIv))

        }

        holder.findById<TextView>(R.id.name_tv).text = item.name
        holder.findById<TextView>(R.id.date_tv).text = item.validPeriod?.let { DateHelper.convertTimeToDateStr(it) }.let {
            "有效期：${it}"
        }

    }
}