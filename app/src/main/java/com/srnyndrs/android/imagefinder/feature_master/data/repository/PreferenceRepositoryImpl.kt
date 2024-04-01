package com.srnyndrs.android.imagefinder.feature_master.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.srnyndrs.android.imagefinder.feature_master.domain.repository.PreferenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferenceRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
): PreferenceRepository {

    private companion object {
        val KEY_NAME = stringPreferencesKey(
            name = "search_query"
        )
    }

    override suspend fun setSearchQuery(name: String) {
        dataStore.edit { preference ->
            preference[KEY_NAME] = name
        }
    }

    override fun getSearchQuery(): Flow<String> {
        return dataStore.data
            .catch {
                emit(emptyPreferences())
            }
            .map { preference ->
                preference[KEY_NAME] ?: "dog"
            }
    }
}