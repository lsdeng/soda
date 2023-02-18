package com.hiy.monbie.core

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunminx.architecture.domain.message.MutableResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * auther: liusaideng
 * created on :  2022/11/24 3:11 下午
 * desc:
 */
open abstract class BaseViewModel : ViewModel() {

    val mutableResults = mutableMapOf<String, MutableResult<*>>()

    fun <T> observeState(key: String, lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
        (mutableResults[key] as? MutableResult<T>)?.observe(lifecycleOwner, observer)
    }

    fun <T> dispatchState(key: String, value: T) {
        (mutableResults[key] as? MutableResult<T>)?.postValue(value)
    }

    fun  registerState(key: String, state: MutableResult<*>) {
        mutableResults[key] = state
    }

    fun <T> beginCoroutinesIO(params: T?, callback: (T?) -> Unit) {
        viewModelScope.launch() {
            withContext(Dispatchers.IO) {
                callback.invoke(params)
            }
        }
    }
}