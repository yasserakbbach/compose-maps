package com.yasserakbbach.composemaps.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yasserakbbach.composemaps.domain.usecase.FetchCurrentLongLatUseCase
import com.yasserakbbach.composemaps.manager.LocationPermissionManager
import com.yasserakbbach.composemaps.ui.accesslocation.AccessLocationRoute
import com.yasserakbbach.composemaps.ui.maps.MapsRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val locationPermissionManager: LocationPermissionManager,
    private val fetchCurrentLongLatUseCase: FetchCurrentLongLatUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    init {
        setStartDestination()
    }

    private fun setStartDestination() {
        if (locationPermissionManager.hasLocationPermission()) {
            setCurrentLocationOnMapsStartDestination()
        } else {
            setAccessLocationAsStartDestination()
        }
    }

    private fun setCurrentLocationOnMapsStartDestination(): Any =
        viewModelScope.launch {
            val longLat = fetchCurrentLongLatUseCase().first()
            _state.update {
                it.copy(
                    startDestination = MapsRoute(
                        longitude = longLat.longitude,
                        latitude = longLat.latitude,
                    ),
                )
            }
        }

    private fun setAccessLocationAsStartDestination() {
        _state.update {
            it.copy(startDestination = AccessLocationRoute)
        }
    }
}