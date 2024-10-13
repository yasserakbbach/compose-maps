package com.yasserakbbach.composemaps.ui.accesslocation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yasserakbbach.composemaps.domain.usecase.FetchCurrentLongLatUseCase
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
) : ViewModel() {

    private val _state = MutableStateFlow(AccessLocationState())
    val state = _state.asStateFlow()

    private val _action = Channel<AccessLocationAction>(Channel.BUFFERED)
    val action = _action.receiveAsFlow()

    fun onEvent(event: AccessLocationEvent) {
        when (event) {
            AccessLocationEvent.OnOk -> onOk()
            AccessLocationEvent.OnLocationPermissionGranted -> onLocationPermissionGranted()
            AccessLocationEvent.OnLocationPermissionRejected -> onLocationPermissionRejected()
        }
    }

    private fun onOk() {
        _state.update {
            it.copy(loading = true)
        }
    }

    private fun onLocationPermissionGranted() {
        viewModelScope.launch {
            val longLat = fetchCurrentLongLatUseCase().first()
            _action.send(AccessLocationAction.NavigateToMaps(longLat = longLat))
        }
    }

    private fun onLocationPermissionRejected() {

    }
}