package com.hiy.soda.ui.main

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.hiy.monbie.core.PageViewModel
import com.kunminx.architecture.domain.message.MutableResult

/**
 * auther: liusaideng
 * created on :  2023/1/10 8:57 下午
 * desc:
 */
class MainVm  : PageViewModel() {
    val count: MutableResult<Int> = MutableResult<Int>(0)

    fun observeCountState(lifecycleOwner: LifecycleOwner, observer: Observer<Int>) {
        count.observe(lifecycleOwner, observer)
    }


    override fun onActivityCreated() {
        super.onActivityCreated()
//        viewModelScope.launch() {
//            withContext(Dispatchers.IO) {
//                DBHelper.database.userDao().insertAll(User(name = "lsd"))
//                DBHelper.database.goodsDao().insertAll(Goods(
//                    name = "lsd",
//                    validPeriod = Date()))
//            }
//        }
    }

    override fun getExternalStates(): Map<String, MutableResult<*>> {
        return mapOf()
    }


    override fun loadData() {
    }

}