package com.srnyndrs.android.imagefinder.feature_detail.domain.repository

import com.srnyndrs.android.imagefinder.feature_detail.domain.model.DetailState
import kotlinx.coroutines.flow.Flow

interface ImageDetailRepository {
    fun getImage(id: Long?): Flow<DetailState>
}