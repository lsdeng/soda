package com.hiy.soda.bean.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class RollGoods(
    var barcode: String = "-1",
    var goodsName: String? = null,
    var price: String? = null,
    var brand: String? = null,
    var supplier: String? = null,
    var standard: String? = null
) : Parcelable