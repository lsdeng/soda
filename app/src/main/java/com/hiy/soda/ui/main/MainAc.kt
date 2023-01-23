package com.hiy.soda.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.MultiTypeAdapter
import com.hiy.monbie.core.BaseBusinessAc
import com.hiy.monbie.core.HiyHelper
import com.hiy.monbie.core.PageState
import com.hiy.monbie.core.PageViewModel
import com.hiy.soda.R
import com.hiy.soda.bean.bo.GridBo
import com.hiy.soda.database.DBHelper
import com.hiy.soda.helper.ItemClickSupport
import com.hiy.soda.helper.SodaConstant
import com.hiy.soda.helper.logger
import com.hiy.soda.helper.startup.GsonHelper
import com.hiy.soda.ui.PagingAc
import com.hiy.soda.ui.adapter.GridItemViewBinder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainAc : BaseBusinessAc<MainVm>() {
    private lateinit var mRv: RecyclerView
    private val mData = mutableListOf<Any>()

    override fun getContentLayoutId(): Int {
        return R.layout.ac_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initObserve() {
        viewModel.observePageState<PageState>(PageViewModel.KEY_PAGE_STATE, this, Observer {
            Log.d(HiyHelper.tag, "mainac-pageState${it.desc}")
        })

        viewModel.observeCountState(this, Observer {
            Log.d(HiyHelper.tag, "mainac-count = $it")
        })
    }

    override fun initListener() {
        findViewById<View>(R.id.paging).setOnClickListener {
            Intent(this@MainAc, PagingAc::class.java).apply {
                this@MainAc.startActivity(this)
            }
        }
    }

    override fun onViewCreated(decorView: View) {
        mRv = decorView.findViewById<RecyclerView>(R.id.rv)
        mRv.layoutManager = GridLayoutManager(decorView.context, 4).apply {
            this.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val item = mData[position]
                    return if (item is GridBo) 1 else 4
                }

            }
        }
        mData.add(GridBo("1"))
        mData.add(GridBo("2"))
        mData.add(GridBo("3"))
        mData.add(GridBo("4"))
        val adapter = MultiTypeAdapter(mData).apply {
            register(GridBo::class.java, GridItemViewBinder())
        }
        mRv.adapter = adapter

        ItemClickSupport.addTo(mRv).setOnItemClickListener { recyclerView, position, v ->
            Log.d(SodaConstant.TAG, "item click $position")
        }


        viewModel.loadData()

//        viewModel.dispatchPageState<PageState>(PageViewModel.KEY_PAGE_STATE, PageState.LOADING_OF_BOTTOM)
//        mRv.postDelayed({
//            viewModel.dispatchPageState<PageState>(PageViewModel.KEY_PAGE_STATE, PageState.Content)
//        }, 3000)
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                DBHelper.database.userDao().getAll().onEach {
                    GsonHelper.get().toJson(it).logger()
                }
                DBHelper.database.goodsDao().getAll().onEach {
                    GsonHelper.get().toJson(it).logger()
                }
            }
        }
    }
}
