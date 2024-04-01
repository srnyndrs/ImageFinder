package com.srnyndrs.android.imagefinder.feature_detail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srnyndrs.android.imagefinder.feature_detail.domain.model.DetailState
import com.srnyndrs.android.imagefinder.feature_detail.domain.repository.ImageDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: ImageDetailRepository
): ViewModel() {

    private val _detailState = MutableStateFlow(DetailState(loading = true))
    val detailState = _detailState.asStateFlow()

    fun getImage(id: Long?) {
        viewModelScope.launch {
            repository.getImage(id).collect { result ->
                _detailState.value = result
            }
        }
    }
}