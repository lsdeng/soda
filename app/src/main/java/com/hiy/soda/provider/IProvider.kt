package com.hiy.soda.provider

import com.google.auto.service.AutoService

/**
 * auther: liusaideng
 * created on :  2022/11/30 4:23 下午
 * desc:
 */
interface IProvider {
    fun name(): String
}

class NameProvider : IProvider {
    override fun name(): String {
        return "111"
    }

}