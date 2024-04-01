package com.srnyndrs.android.imagefinder.feature_detail.domain.model

data class DetailState(
    val loading: Boolean? = null,
    val failure: String? = null,
    val success: ImageDetail? = null
)