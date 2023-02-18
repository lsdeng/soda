package com.hiy.monbie.core.ext

import android.view.View
import android.widget.TextView
import com.hiy.monbie.core.BaseBusinessAc
import com.hiy.monbie.core.PageViewModel
import com.hiy.monbie.core.R

fun BaseBusinessAc<PageViewModel>.setTitle(view: View, title: String, listener: View.OnClickListener?) {
    view.findViewById<TextView>(R.id.title_tv).text = title
    view.findViewById<TextView>(R.id.right_tv).apply {
        text = "确定"
        setOnClickListener {
            listener?.onClick(it)
//            if (viewModel.goods.value?.isValid() == true) {
//                lifecycleScope.launch {
//                    withContext(Dispatchers.IO) {
//                        DBHelper.database.goodsDao().insertAll(viewModel.goods.value!!)
//                        DBHelper.database.goodsDao().getAll().onEach {
//                            GsonHelper.get().toJson(it).logger()
//                        }
//                        withContext(Dispatchers.Main) {
//                            finish()
//
//                        }
//                    }
//                }
//            } else {
//                Toast.makeText(this@AddGoodsAc, "xxx", Toast.LENGTH_SHORT).show()
//            }
        }
    }
}