package com.yasserakbbach.composemaps.ui.accesslocation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yasserakbbach.composemaps.domain.usecase.FetchCurrentLongLatUseCase
import com.yasserakbbach.composemaps.domain.usecase.IncrementLocationPermissionRejectedCountUseCase
import com.yasserakbbach.composemaps.domain.usecase.ShouldLocationPermissionRequestedFromSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccessLocationViewModel @Inject constructor(
    private val fetchCurrentLongLatUseCase: FetchCurrentLongLatUseCase,
    private val shouldLocationPermissionRequestedFromSettingsUseCase: ShouldLocationPermissionRequestedFromSettingsUseCase,
    private val incrementLocationPermissionRejectedCountUseCase: IncrementLocationPermissionRejectedCountUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(AccessLocationState())
    val state = _state.asStateFlow()

    private val _action = Channel<AccessLocationAction>(Channel.BUFFERED)
    val action = _action.receiveAsFlow()

    init {
        shouldRequestLocationPermissionFromSettings()
    }

    fun onEvent(event: AccessLocationEvent) {
        when (event) {
            AccessLocationEvent.OnOk -> onOk()
            AccessLocationEvent.OnLocationPermissionGranted -> onLocationPermissionGranted()
            AccessLocationEvent.OnLocationPermissionRejected -> onLocationPermissionRejected()
        }
    }

    private fun onOk() {
        toggleLoading(loading = true)
    }

    private fun onLocationPermissionGranted() {
        viewModelScope.launch {
            val longLat = fetchCurrentLongLatUseCase().first()
            _action.send(AccessLocationAction.NavigateToMaps(longLat = longLat))
        }
    }

    private fun onLocationPermissionRejected() {
        viewModelScope.launch {
            incrementLocationPermissionRejectedCountUseCase()
            toggleLoading(loading = false)
            shouldRequestLocationPermissionFromSettings()
        }
    }

    private fun shouldRequestLocationPermissionFromSettings() {
        viewModelScope.launch {
            val allowLocationPermissionFromSettings = shouldLocationPermissionRequestedFromSettingsUseCase().first()
            _state.update {
                it.copy(
                    allowLocationPermissionFromSettings = allowLocationPermissionFromSettings,
                )
            }
        }
    }

    private fun toggleLoading(loading: Boolean) {
        _state.update {
            it.copy(loading = loading)
        }
    }
}