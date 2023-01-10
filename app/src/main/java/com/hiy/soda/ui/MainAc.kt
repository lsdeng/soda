package com.hiy.soda.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.drakeet.multitype.MultiTypeAdapter
import com.hiy.monbie.core.BaseBusinessAc
import com.hiy.monbie.core.HiyHelper
import com.hiy.monbie.core.PageState
import com.hiy.monbie.core.PageViewModel
import com.hiy.soda.R
import com.hiy.soda.bean.bo.GridBo
import com.hiy.soda.bean.dto.User
import com.hiy.soda.database.AppDatabase
import com.hiy.soda.helper.ItemClickSupport
import com.hiy.soda.helper.SodaConstant
import com.hiy.soda.helper.startup.GsonHelper
import com.hiy.soda.provider.IProvider
import com.hiy.soda.ui.adapter.GridItemViewBinder
import com.hiy.soda.ui.fg.LifeCycleFg
import com.kunminx.architecture.domain.message.MutableResult
import kotlinx.coroutines.*
import java.util.*

class MainAc : BaseBusinessAc<CountViewModel>() {
    val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()
    }


    private lateinit var mRv: RecyclerView
    private val mData = mutableListOf<Any>()

    override fun getViewModelClass(): Class<CountViewModel> {
        return CountViewModel::class.java
    }

    override fun getContentLayoutId(): Int {
        return R.layout.ac_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initViewModel() {
        lifecycleScope.launch() {
            withContext(Dispatchers.IO) {
                database.userDao().insertAll(User(name = "lsd"))
            }
        }
    }

    override fun initObserve() {
        viewModel.observePageState(this, Observer {
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
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                database.userDao().getAll().onEach { GsonHelper.get().toJson(it) }
            }
        }
    }
}


class CountViewModel : PageViewModel() {
    val count: MutableResult<Int> = MutableResult<Int>(0)


    fun observeCountState(lifecycleOwner: LifecycleOwner, observer: Observer<Int>) {
        count.observe(lifecycleOwner, observer)
    }

    override fun initData() {
        super.initData()
    }


    fun loadData() {
        Thread {
            Thread.sleep(1000)
            count.postValue(1000)
            dispatchPageState(PageState.Content)
        }.start()
    }
}
