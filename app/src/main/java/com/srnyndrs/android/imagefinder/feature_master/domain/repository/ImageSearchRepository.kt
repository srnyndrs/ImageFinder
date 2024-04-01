package com.srnyndrs.android.imagefinder.feature_master.domain.repository

import androidx.paging.PagingData
import com.srnyndrs.android.imagefinder.feature_master.domain.model.ImageResult
import kotlinx.coroutines.flow.Flow

interface ImageSearchRepository {
    fun searchImages(text: String): Flow<PagingData<ImageResult>>
}