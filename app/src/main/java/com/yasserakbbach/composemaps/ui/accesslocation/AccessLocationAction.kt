package com.yasserakbbach.composemaps.ui.accesslocation

import com.yasserakbbach.composemaps.domain.model.LongLat

sealed interface AccessLocationAction {
    data class NavigateToMaps(val longLat: LongLat) : AccessLocationAction
}