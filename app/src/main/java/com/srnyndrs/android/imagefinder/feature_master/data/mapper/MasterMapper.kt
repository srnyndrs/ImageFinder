package com.srnyndrs.android.imagefinder.feature_master.data.mapper

import com.srnyndrs.android.imagefinder.core.data.remote.dto.SearchPhoto
import com.srnyndrs.android.imagefinder.feature_master.domain.model.ImageResult

fun SearchPhoto.toImageResult(): ImageResult {
    return ImageResult(
        id = id,
        imageUrl = "https://live.staticflickr.com/${server}/${id}_${secret}.jpg"
    )
}