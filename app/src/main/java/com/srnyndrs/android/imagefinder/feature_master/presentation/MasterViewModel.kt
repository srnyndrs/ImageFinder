package com.srnyndrs.android.imagefinder.feature_master.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.srnyndrs.android.imagefinder.feature_master.domain.model.ImageResult
import com.srnyndrs.android.imagefinder.feature_master.domain.repository.ImageSearchRepository
import com.srnyndrs.android.imagefinder.feature_master.domain.repository.PreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MasterViewModel @Inject constructor(
    private val repository: ImageSearchRepository,
    private val preference: PreferenceRepository
): ViewModel() {

    private val _pagingState = MutableStateFlow<PagingData<ImageResult>>(PagingData.empty())
    val pagingState = _pagingState.asStateFlow().cachedIn(viewModelScope)

    private val _searchQueryState = MutableStateFlow("")
    val searchQueryState = _searchQueryState.asStateFlow()

    init {
        viewModelScope.launch {
            preference.getSearchQuery().collect {
                _searchQueryState.value = it
                searchImages(it)
            }
        }
    }

    fun searchImages(name: String) {
        viewModelScope.launch {
            try {
                repository
                    .searchImages(name)
                    .collect { pagingData ->
                        _pagingState.value = pagingData
                        setQueryPreference(name)
                    }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun setSearchQuery(value: String) {
        _searchQueryState.value = value
    }

    private fun setQueryPreference(name: String) {
        viewModelScope.launch {
            preference.setSearchQuery(name)
        }
    }
}