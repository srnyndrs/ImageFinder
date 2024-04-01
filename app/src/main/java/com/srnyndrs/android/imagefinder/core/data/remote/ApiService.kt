package com.srnyndrs.android.imagefinder.core.data.remote

import com.srnyndrs.android.imagefinder.core.data.remote.dto.PhotoResponseDto
import com.srnyndrs.android.imagefinder.core.data.remote.dto.SearchResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object {
        const val BASE_URL = "https://www.flickr.com/services/rest/"
        const val API_KEY = ""
    }

    @GET("?method=flickr.photos.search&format=json&nojsoncallback=1&api_key=$API_KEY")
    suspend fun searchImages(
        @Query("text") text: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 20
    ): SearchResponseDto

    @GET("?method=flickr.photos.getInfo&format=json&nojsoncallback=1&api_key=$API_KEY")
    suspend fun getImage(
        @Query("photo_id") photoId: Long?
    ): PhotoResponseDto
}