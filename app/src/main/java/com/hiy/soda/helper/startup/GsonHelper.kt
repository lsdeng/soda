package com.hiy.soda.helper.startup

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * auther: liusaideng
 * created on :  2023/1/3 3:37 下午
 * desc:
 */
object GsonHelper {
    private val gson: Gson by lazy {
        GsonBuilder().setExclusionStrategies(object : ExclusionStrategy {
            override fun shouldSkipField(f: FieldAttributes?): Boolean =
                f?.name?.startsWith("io_realm_kotlin_") ?: false

            override fun shouldSkipClass(clazz: Class<*>?): Boolean =
                false
        })
            .create()
    }

    fun get(): Gson {
        return gson
    }
}