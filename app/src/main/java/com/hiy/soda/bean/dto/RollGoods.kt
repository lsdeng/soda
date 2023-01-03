package com.hiy.soda.bean.dto

import io.realm.kotlin.types.ObjectId
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.parcelize.RawValue

class RollGoods() : RealmObject {
    @PrimaryKey
    var _id: @RawValue ObjectId = ObjectId.create()
    var goodsName: String? = null
    var brand: String? = null
    var validPeriod: Long? = null
}
