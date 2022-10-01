package com.hiy.soda.bean.common

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class RollResListDTO<T : Parcelable>(
    var code: Int? = null,
    var msg: String? = null,
    var data: List<T>? = null
) : Parcelable