package com.srnyndrs.android.imagefinder.core.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SearchResponseDto (
    val photos: SearchPhotos? = null,
    val stat: String,
    val code: Int? = null,
    val message: String? = null
)

data class SearchPhotos (
    val page: Int,
    val pages: Long,
    @SerializedName("perpage")
    val perPage: Long,
    val total: Long,
    val photo: List<SearchPhoto>
)

data class SearchPhoto (
    val id: String,
    val owner: String? = null,
    val secret: String? = null,
    val server: String? = null,
    val farm: Long? = null,
    val title: String? = null,
    @SerializedName("ispublic")
    val isPublic: Int? = null,
    @SerializedName("isfriend")
    val isFriend: Int? = null,
    @SerializedName("isfamily")
    val isFamily: Int? = null
)