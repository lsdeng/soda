package com.hiy.soda.ui.main

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.hiy.monbie.core.BaseBusinessAc
import com.hiy.monbie.core.HiyHelper
import com.hiy.monbie.core.PageState
import com.hiy.monbie.core.PageViewModel
import com.hiy.soda.R
import com.hiy.soda.helper.visible
import com.hiy.soda.service.vm.GoodsVM
import com.hiy.soda.ui.GoodsListAc

class MainAc : BaseBusinessAc<MainVm>() {

    private lateinit var mRv: RecyclerView
    private val mData = mutableListOf<Any>()
    private lateinit var mGoodsTotalCountTv: TextView
    private val mGoodsVm: GoodsVM by lazy {
        ViewModelProvider.NewInstanceFactory().create(GoodsVM::class.java)
    }


    override fun getContentLayoutId(): Int {
        return R.layout.ac_main
    }

    override fun initToolbar(view: View) {
        view.visible(false)
    }

    override fun initObserve() {
        viewModel.observeState<PageState>(PageViewModel.KEY_PAGE_STATE, this, Observer {
            Log.d(HiyHelper.tag, "mainac-pageState${it.desc}")
        })

        viewModel.observeCountState(this, Observer {
            Log.d(HiyHelper.tag, "mainac-count = $it")
        })

        mGoodsVm.observeState<Int>(GoodsVM.KEY_TOTAL_COUNT, this, Observer {
            mGoodsTotalCountTv.text = "商品数：${it ?: 0} "
        })
    }

    override fun initListener() {
        mGoodsTotalCountTv.setOnClickListener {
            GoodsListAc.navGoodsList(this@MainAc)
        }
//        findViewById<View>(R.id.paging).setOnClickListener {
//            Intent(this@MainAc, PagingAc::class.java).apply {
//                this@MainAc.startActivity(this)
//            }
//        }
//
//        findViewById<View>(R.id.goods_list).setOnClickListener {
//        }
//
//        findViewById<View>(R.id.fg).setOnClickListener {
//            WrapperActivity.nav(this@MainAc, BlackFragment::class.java, null)
//        }
    }

    override fun onViewCreated(decorView: View) {
        mGoodsTotalCountTv = decorView.findViewById(R.id.goods_total_count_tv)
//
//        mRv = decorView.findViewById<RecyclerView>(R.id.rv)
//        mRv.layoutManager = GridLayoutManager(decorView.context, 4).apply {
//            this.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
//                override fun getSpanSize(position: Int): Int {
//                    val item = mData[position]
//                    return if (item is GridBo) 1 else 4
//                }
//
//            }
//        }
//        mData.add(GridBo("1"))
//        mData.add(GridBo("2"))
//        mData.add(GridBo("3"))
//        mData.add(GridBo("4"))
//        val adapter = MultiTypeAdapter(mData).apply {
//            register(GridBo::class.java, GridItemViewBinder())
//        }
//        mRv.adapter = adapter
//
//        ItemClickSupport.addTo(mRv).setOnItemClickListener { recyclerView, position, v ->
//            Log.d(SodaConstant.TAG, "item click $position")
//        }
//

//        viewModel.loadData()


//        findViewById<View>(R.id.add_goods).setOnClickListener {
//            AddGoodsAc.navAddGoods(this@MainAc)
//        }

//        mRv.postDelayed({
//            viewModel.dispatchState<PageState>(PageViewModel.KEY_PAGE_STATE, PageState.Content)
//        }, 1000)
    }


    override fun loadData() {
        super.loadData()
        mGoodsVm.queryGoodsTotal()
    }

    override fun onResume() {
        super.onResume()

        mGoodsVm.queryGoodsTotal()
//        lifecycleScope.launch {
//            withContext(Dispatchers.IO) {
//                DBHelper.database.userDao().getAll().onEach {
//                    GsonHelper.get().toJson(it).logger()
//                }
//                DBHelper.database.goodsDao().getAll().onEach {
//                    GsonHelper.get().toJson(it).logger()
//                }
//            }
//        }
    }


}
