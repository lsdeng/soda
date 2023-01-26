package com.hiy.soda.ui

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.MultiTypeAdapter
import com.hiy.monbie.core.BaseBusinessAc
import com.hiy.monbie.core.PageViewModel
import com.hiy.soda.R
import com.hiy.soda.bean.dto.Goods
import com.hiy.soda.database.DBHelper
import com.hiy.soda.ui.adapter.GoodsItemViewBinder
import com.kunminx.architecture.domain.message.MutableResult

class GoodsListAc : BaseBusinessAc<GoodsListVM>() {

    companion object {
        fun navGoodsList(ac: Activity) {
            Intent(ac, GoodsListAc::class.java).apply {
                ac.startActivity(this)
            }
        }
    }

    private lateinit var mRv: RecyclerView
    private var mData = mutableListOf<Goods>()
    private val adapter: MultiTypeAdapter by lazy {
        MultiTypeAdapter(mData)
    }


    override fun initToolbar(view: View) {
        view.findViewById<TextView>(R.id.title_tv).text = "商品列表"
    }

    override fun getContentLayoutId(): Int {
        return R.layout.ac_goods_list
    }

    override fun onViewCreated(decorView: View) {
        mRv = decorView.findViewById(R.id.rv)
        mRv.layoutManager = LinearLayoutManager(this@GoodsListAc)
        adapter.register(Goods::class.java, GoodsItemViewBinder())
        mRv.adapter = adapter
    }

    override fun initListener() {
    }

    override fun initObserve() {
        viewModel.observePageState<List<Goods>>(GoodsListVM.KEY_DATA_LIST_SOURCE, this@GoodsListAc) {
            mData.clear()
            mData.addAll(it)
            adapter.notifyDataSetChanged()
        }
    }
}


class GoodsListVM : PageViewModel() {

    companion object {
        const val KEY_DATA_LIST_SOURCE = "KEY_DATA_LIST_SOURCE"
    }

    private val dataSource = MutableResult<List<Goods>>(listOf<Goods>())

    override fun getExternalStates(): Map<String, MutableResult<*>> {
        return mapOf(KEY_DATA_LIST_SOURCE to dataSource)
    }

    override fun loadData() {
        fetchGoodsList()
    }

    private fun fetchGoodsList() {
        beginCoroutinesIO(null) {
            DBHelper.database.goodsDao().getAll().apply {
                dataSource.postValue(this)
            }
        }
    }


}