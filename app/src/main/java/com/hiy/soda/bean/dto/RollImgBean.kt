package com.hiy.soda.bean.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class RollImg(
    var imageFileLength: Int?,
    var imageSize: String?,
    var imageUrl: String?
) : Parcelable