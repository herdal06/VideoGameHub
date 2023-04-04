package com.herdal.videogamehub.presentation.game_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.herdal.videogamehub.common.Resource
import com.herdal.videogamehub.domain.use_case.game.GetGameDetailsUseCase
import com.herdal.videogamehub.domain.use_case.game_screenshots.GetScreenshotsUseCase
import com.herdal.videogamehub.domain.use_case.trailer.GetGameTrailersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameDetailViewModel @Inject constructor(
    private val getGameDetailsUseCase: GetGameDetailsUseCase,
    private val getScreenshotsUseCase: GetScreenshotsUseCase,
    private val getGameTrailersUseCase: GetGameTrailersUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(GameDetailUiState())
    val uiState: StateFlow<GameDetailUiState> = _uiState.asStateFlow()

    fun handleEvent(event: GameDetailUiEvent) {
        when (event) {
            is GameDetailUiEvent.GetGameById -> getGameById(event.id)
            is GameDetailUiEvent.GetScreenshots -> getScreenshots(event.gameId)
            is GameDetailUiEvent.GetTrailers -> getTrailers(event.gameId)
        }
    }

    fun getGameById(id: Int) = viewModelScope.launch {
        getGameDetailsUseCase.invoke(id).collect { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _uiState.update { state ->
                        state.copy(isLoading = true)
                    }
                }
                is Resource.Success -> {
                    _uiState.update { state ->
                        state.copy(gameDetail = resource.data)
                    }
                }
                is Resource.Error -> {
                    _uiState.update { state ->
                        state.copy(error = resource.message)
                    }
                }
            }
        }
    }

    fun getScreenshots(gameId: Int) = viewModelScope.launch {
        getScreenshotsUseCase.invoke(gameId).collect { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _uiState.update { state ->
                        state.copy(isLoading = true)
                    }
                }
                is Resource.Success -> {
                    _uiState.update { state ->
                        state.copy(screenshots = resource.data)
                    }
                }
                is Resource.Error -> {
                    _uiState.update { state ->
                        state.copy(error = resource.message)
                    }
                }
            }
        }
    }

    fun getTrailers(gameId: Int) = viewModelScope.launch {
        getGameTrailersUseCase(gameId).onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _uiState.update { state ->
                        state.copy(isLoading = true)
                    }
                }
                is Resource.Success -> {
                    _uiState.update { state ->
                        state.copy(trailers = resource.data)
                    }
                }
                is Resource.Error -> {
                    _uiState.update { state ->
                        state.copy(error = resource.message)
                    }
                }
            }
        }
    }
}
