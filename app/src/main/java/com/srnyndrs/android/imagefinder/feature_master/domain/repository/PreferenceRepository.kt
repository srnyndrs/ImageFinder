package com.srnyndrs.android.imagefinder.feature_master.domain.repository

import kotlinx.coroutines.flow.Flow

interface PreferenceRepository {

    fun getSearchQuery(): Flow<String>

    suspend fun setSearchQuery(name: String)
}