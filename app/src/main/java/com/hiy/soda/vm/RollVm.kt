package com.hiy.soda.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.hiy.soda.SodaApp
import com.hiy.soda.bean.dto.RollGoods
import com.hiy.soda.bean.dto.RollImg
import com.hiy.soda.dao.RollDatabase

class RollVm : ViewModel() {

    val rollDb by lazy {
        Room.databaseBuilder(SodaApp.application.applicationContext, RollDatabase::class.java, "roll")
            .build()
    }

    lateinit var appId: String

    lateinit var appSecret: String

    val goods = MutableLiveData<List<RollGoods>>(listOf())

    val girls = MutableLiveData<List<RollImg>>(listOf())

    val countflow by lazy {
        Flow
    }


    fun attachGoods() {
//        Thread {
//            rollDb.rollGoodsDao()?.getAll().apply {
//                goods.postValue(this)
//                try {
//                    goods.value = this
//                } catch (e: Exception) {
//
//                }
//            }
//        }.start()

    }


}

