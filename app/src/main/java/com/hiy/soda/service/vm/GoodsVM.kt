package com.hiy.soda.service.vm

import com.hiy.monbie.core.BaseViewModel
import com.hiy.soda.database.DBHelper
import com.kunminx.architecture.domain.message.MutableResult

class GoodsVM : BaseViewModel() {

    companion object {
        const val KEY_TOTAL_COUNT = "total_count"
        const val KEY_DELETE = "delete"
    }

    private val totalCountState = MutableResult<Int>()
    private val deleteState = MutableResult<Int>()

    init {
        registerState(KEY_TOTAL_COUNT, totalCountState)
        registerState(KEY_DELETE, deleteState)
    }

    fun queryGoodsTotal() {
        beginCoroutinesIO(null) {
            val size = DBHelper.database.goodsDao().getAll().size
            totalCountState.postValue(size)
        }
    }

    fun deleteGoods(id: String) {
        beginCoroutinesIO(id) {
            DBHelper.database.goodsDao().delete(id)
            dispatchState(KEY_DELETE, 0)
        }
    }
}