package com.yasserakbbach.composemaps.ui.maps

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.yasserakbbach.composemaps.domain.model.LongLat
import com.yasserakbbach.composemaps.domain.usecase.GetCountryFromLongLatUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getCountryFromLongLatUseCase: GetCountryFromLongLatUseCase,
) : ViewModel() {

    private val args = savedStateHandle.toRoute<MapsRoute>()
    private val longLat by lazy {
        LongLat(
            longitude = args.longitude,
            latitude = args.latitude,
        )
    }
    private val _state = MutableStateFlow(
        MapsState(
            longitude = longLat.longitude,
            latitude = longLat.latitude,
        )
    )
    val state = _state.asStateFlow()

    init {
        fetchCountryName()
    }

    private fun fetchCountryName() {
        viewModelScope.launch {
            val country = getCountryFromLongLatUseCase(longLat = longLat) ?: return@launch
            _state.update {
                it.copy(
                    countryNameWithCode = "${country.code} - ${country.name}",
                    loading = false
                )
            }
        }
    }
}