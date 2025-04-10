package com.test.fetchapp.view

import com.test.fetchapp.data.GroupedItem

sealed class ScreenState {
    object Loading: ScreenState()
    data class ContentLoaded(val items: List<GroupedItem>): ScreenState()
    data class Error(val error: String): ScreenState()
}