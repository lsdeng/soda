package com.hiy.soda.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.hiy.monbie.core.BaseBusinessAc
import com.hiy.monbie.core.HiyHelper
import com.hiy.monbie.core.PageState
import com.hiy.monbie.core.PageViewModel
import com.hiy.soda.R
import com.hiy.soda.bean.dto.RollGoods
import com.hiy.soda.provider.IProvider
import com.hiy.soda.ui.fg.LifeCycleFg
import com.kunminx.architecture.domain.message.MutableResult
import java.util.*

class MainAc : BaseBusinessAc<CountViewModel>() {

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
        Log.d(HiyHelper.tag, "onViewCreated")
        viewModel.loadData()
        val bookList = ServiceLoader.load(IProvider::class.java, javaClass.classLoader).toList()
        bookList.forEach {
            Log.d("MainActivity", it.name())
        }



        findViewById<View>(R.id.wsc).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)

            intent.data = Uri.parse("wsc://webview?url=${Uri.encode("www.baidu.com")}")
            intent.resolveActivity(this@MainAc.packageManager)?.let {
                this@MainAc.startActivity(intent)
            }
        }

        val fg = LifeCycleFg()
        supportFragmentManager.beginTransaction().add(R.id.fg_container, fg).commit()
    }

    override fun onResume() {
        super.onResume()
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
