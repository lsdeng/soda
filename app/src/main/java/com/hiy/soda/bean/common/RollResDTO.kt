package com.hiy.soda.bean.common

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class RollResDTO<T : Parcelable>(
    var code: Int? = null,
    var msg: String? = null,
    var data: T? = null
) : Parcelable