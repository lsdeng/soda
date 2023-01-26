package com.hiy.soda.ui.main

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.hiy.monbie.core.PageViewModel
import com.hiy.soda.bean.dto.Goods
import com.hiy.soda.bean.dto.User
import com.hiy.soda.database.DBHelper
import com.kunminx.architecture.domain.message.MutableResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

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
        viewModelScope.launch() {
            withContext(Dispatchers.IO) {
                DBHelper.database.userDao().insertAll(User(name = "lsd"))
                DBHelper.database.goodsDao().insertAll(Goods(
                    name = "lsd",
                    validPeriod = Date()))
            }
        }
    }

    override fun getExternalStates(): Map<String, MutableResult<*>> {
        return mapOf()
    }


    override fun loadData() {
    }

}