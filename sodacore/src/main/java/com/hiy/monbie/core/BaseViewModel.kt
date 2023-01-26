package com.hiy.monbie.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * auther: liusaideng
 * created on :  2022/11/24 3:11 下午
 * desc:
 */
open abstract class BaseViewModel : ViewModel() {


    fun <T> beginCoroutinesIO(params: T, callback: (T) -> Unit) {
        viewModelScope.launch() {
            withContext(Dispatchers.IO) {
                callback.invoke(params)
            }
        }
    }

    abstract fun loadData()
}