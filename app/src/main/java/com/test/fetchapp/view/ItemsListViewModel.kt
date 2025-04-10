package com.test.fetchapp.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.fetchapp.domain.usecases.FetchGroupedItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemsListViewModel @Inject constructor(private val useCase: FetchGroupedItemsUseCase): ViewModel() {

    private val _state = MutableStateFlow<ScreenState>(ScreenState.Loading)
    val state: StateFlow<ScreenState> = _state.asStateFlow()

    fun fetchItems() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.fetchGroupedItems().onSuccess {
                _state.emit(ScreenState.ContentLoaded(items = it))
            }.onFailure {
                _state.emit(ScreenState.Error(it.message ?: "Unknown Error"))
            }
        }
    }
}