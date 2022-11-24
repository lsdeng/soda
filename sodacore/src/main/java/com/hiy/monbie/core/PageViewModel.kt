package com.hiy.monbie.core

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.kunminx.architecture.domain.message.MutableResult

/**
 * auther: liusaideng
 * created on :  2022/11/24 3:11 下午
 * desc:
 */
open abstract class PageViewModel : BaseViewModel {

    constructor() : super() {
        pageState.value = PageState.Content
        initDataInner()
    }

    private val pageState: MutableResult<PageState> = MutableResult.Builder<PageState>()
        .setAllowNullValue(false)
        .create()

    fun observePageState(lifecycleOwner: LifecycleOwner, observer: Observer<PageState>) {
        pageState.observe(lifecycleOwner, observer)
    }


    private fun initDataInner() {
        Log.d(HiyHelper.tag, "initDataInner ${pageState.value}")
        initData()
    }

    protected abstract fun initData()

}