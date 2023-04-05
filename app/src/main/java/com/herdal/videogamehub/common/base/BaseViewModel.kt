package com.herdal.videogamehub.common.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<T : Any> : ViewModel() {

    private val _uiState = MutableStateFlow<T>(getInitialUiState())
    val uiState: StateFlow<T> = _uiState.asStateFlow()

    protected abstract fun getInitialUiState(): T

    protected fun updateUiState(updateState: (currentState: T) -> T) {
        _uiState.update { currentState ->
            updateState(currentState)
        }
    }

    fun handleEvent(event: Any) {
        // Default implementation, can be overridden in subclasses
    }
}