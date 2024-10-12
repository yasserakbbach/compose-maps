package com.yasserakbbach.composemaps.ui.accesslocation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AccessLocationViewModel @Inject constructor(

) : ViewModel() {

    private val _state = MutableStateFlow(AccessLocationState())
    val state = _state.asStateFlow()

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

    }

    private fun onLocationPermissionRejected() {

    }
}