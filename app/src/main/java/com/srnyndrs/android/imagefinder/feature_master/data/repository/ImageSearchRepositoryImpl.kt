package com.srnyndrs.android.imagefinder.feature_master.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.srnyndrs.android.imagefinder.core.data.remote.ApiService
import com.srnyndrs.android.imagefinder.feature_master.data.paging.ImagePagingSource
import com.srnyndrs.android.imagefinder.feature_master.domain.model.ImageResult
import com.srnyndrs.android.imagefinder.feature_master.domain.repository.ImageSearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ImageSearchRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): ImageSearchRepository {
    override fun searchImages(text: String): Flow<PagingData<ImageResult>> {
        return Pager(
            pagingSourceFactory = {
                ImagePagingSource(
                    apiService = apiService,
                    query = text
                )
            },
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 1,
                initialLoadSize = 1
            )
        ).flow
    }
}