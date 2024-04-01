package com.srnyndrs.android.imagefinder.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.srnyndrs.android.imagefinder.core.data.remote.ApiService
import com.srnyndrs.android.imagefinder.core.data.remote.ApiService.Companion.BASE_URL
import com.srnyndrs.android.imagefinder.feature_detail.data.repository.ImageDetailRepositoryImpl
import com.srnyndrs.android.imagefinder.feature_detail.domain.repository.ImageDetailRepository
import com.srnyndrs.android.imagefinder.feature_master.data.repository.ImageSearchRepositoryImpl
import com.srnyndrs.android.imagefinder.feature_master.domain.repository.ImageSearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideImageSearchRepository(apiService: ApiService): ImageSearchRepository {
        return ImageSearchRepositoryImpl(apiService)
    }

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context) : DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            produceFile = { context.preferencesDataStoreFile("search_query") }
        )
    }

    @Singleton
    @Provides
    fun provideImageDetailRepository(apiService: ApiService): ImageDetailRepository {
        return ImageDetailRepositoryImpl(apiService)
    }
}