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
open abstract class PageViewModel : BaseViewModel() {

    private val pageState: MutableResult<PageState> = MutableResult.Builder<PageState>()
        .setAllowNullValue(false)
        .create()

    fun observePageState(lifecycleOwner: LifecycleOwner, observer: Observer<PageState>) {
        pageState.observe(lifecycleOwner, observer)
    }

    fun dispatchPageState(value: PageState) {
        pageState.postValue(value)
    }

    open fun initData() {
        Log.d(HiyHelper.tag, "initData")
        dispatchPageState(PageState.LOADING_OF_BOTTOM)
    }

}