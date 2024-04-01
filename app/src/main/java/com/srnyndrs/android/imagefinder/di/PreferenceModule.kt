package com.srnyndrs.android.imagefinder.di

import com.srnyndrs.android.imagefinder.feature_master.data.repository.PreferenceRepositoryImpl
import com.srnyndrs.android.imagefinder.feature_master.domain.repository.PreferenceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
abstract class PreferenceModule {

    @Binds
    abstract fun bindPreference(impl: PreferenceRepositoryImpl): PreferenceRepository
}