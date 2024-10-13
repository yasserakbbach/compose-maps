package com.yasserakbbach.composemaps.ui.maps

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val args = savedStateHandle.toRoute<MapsRoute>()
    private val _state = MutableStateFlow(
        MapsState(
            longitude = args.longitude,
            latitude = args.latitude,
        )
    )
    val state = _state.asStateFlow()


}