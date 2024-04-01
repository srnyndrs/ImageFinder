package com.srnyndrs.android.imagefinder.feature_detail.data.mapper

import com.srnyndrs.android.imagefinder.core.data.remote.dto.Photo
import com.srnyndrs.android.imagefinder.feature_detail.domain.model.ImageDetail

fun Photo.toImageInfo(): ImageDetail {
    return ImageDetail(
        imageUrl = "https://live.staticflickr.com/${server}/${id}_${secret}.jpg",
        description = if(!description?.content.isNullOrBlank()) description?.content!! else "No description provided",
    )
}