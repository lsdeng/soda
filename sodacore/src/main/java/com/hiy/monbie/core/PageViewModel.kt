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

    companion object {
        const val KEY_PAGE_STATE = "pageState"
    }

    private val mutableResults = mutableMapOf<String, MutableResult<*>>()

    private val pageState: MutableResult<PageState> = MutableResult.Builder<PageState>()
        .setAllowNullValue(false)
        .create()

    fun <T> observePageState(key: String, lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
        (mutableResults[key] as? MutableResult<T>)?.observe(lifecycleOwner, observer)
    }

    fun <T> dispatchPageState(key: String, value: T) {
        (mutableResults[key] as? MutableResult<T> )?.postValue(value)
    }

    open fun onActivityCreated() {
        Log.d(HiyHelper.tag, "initData")
        mutableResults.putAll(getRegisterStates() + getExternalStates())
    }



     private fun getRegisterStates() : Map<String, MutableResult<*>> {
         return mapOf<String, MutableResult<*>>(
             KEY_PAGE_STATE to pageState
         )
     }

    abstract fun getExternalStates() : Map<String, MutableResult<*>>

}