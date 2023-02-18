package com.hiy.monbie.core

import android.util.Log
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


    private val pageState: MutableResult<PageState> = MutableResult.Builder<PageState>()
        .setAllowNullValue(false)
        .create()



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


    abstract fun loadData()

}