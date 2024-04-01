package com.srnyndrs.android.imagefinder.feature_detail.data.repository

import com.srnyndrs.android.imagefinder.core.data.remote.ApiService
import com.srnyndrs.android.imagefinder.feature_detail.data.mapper.toImageInfo
import com.srnyndrs.android.imagefinder.feature_detail.domain.model.DetailState
import com.srnyndrs.android.imagefinder.feature_detail.domain.repository.ImageDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ImageDetailRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): ImageDetailRepository {
    override fun getImage(id: Long?): Flow<DetailState> {
        return flow {
            try {
                val response = apiService.getImage(id)
                emit(DetailState(success = response.photo.toImageInfo()))
            } catch (e: Exception) {
                emit(DetailState(failure = e.message.toString()))
            }
        }
    }
}