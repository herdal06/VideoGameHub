package com.herdal.videogamehub.presentation.game_detail

import androidx.lifecycle.viewModelScope
import com.herdal.videogamehub.common.Resource
import com.herdal.videogamehub.common.base.BaseViewModel
import com.herdal.videogamehub.domain.use_case.game.GetGameDetailsUseCase
import com.herdal.videogamehub.domain.use_case.game_screenshots.GetScreenshotsUseCase
import com.herdal.videogamehub.domain.use_case.trailer.GetGameTrailersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameDetailViewModel @Inject constructor(
    private val getGameDetailsUseCase: GetGameDetailsUseCase,
    private val getScreenshotsUseCase: GetScreenshotsUseCase,
    private val getGameTrailersUseCase: GetGameTrailersUseCase
) : BaseViewModel<GameDetailUiState>() {

    override fun getInitialUiState(): GameDetailUiState = GameDetailUiState()

    fun handleEvent(event: GameDetailUiEvent) {
        when (event) {
            is GameDetailUiEvent.GetGameById -> getGameById(event.id)
            is GameDetailUiEvent.GetScreenshots -> getScreenshots(event.gameId)
            is GameDetailUiEvent.GetTrailers -> getTrailers(event.gameId)
        }
    }

    fun getGameById(id: Int) = viewModelScope.launch {
        getGameDetailsUseCase.invoke(id).collect { resource ->
            updateUiState { state ->
                when (resource) {
                    is Resource.Error -> state.copy(error = resource.message)
                    is Resource.Loading -> state.copy(isLoading = true)
                    is Resource.Success -> state.copy(gameDetail = resource.data)
                }
            }
        }
    }

    fun getScreenshots(gameId: Int) = viewModelScope.launch {
        getScreenshotsUseCase.invoke(gameId).collect { resource ->
            updateUiState { state ->
                when (resource) {
                    is Resource.Error -> state.copy(error = resource.message)
                    is Resource.Loading -> state.copy(isLoading = true)
                    is Resource.Success -> state.copy(screenshots = resource.data)
                }
            }
        }
    }

    fun getTrailers(gameId: Int) = viewModelScope.launch {
        getGameTrailersUseCase(gameId).onEach { resource ->
            updateUiState { state ->
                when (resource) {
                    is Resource.Error -> state.copy(error = resource.message)
                    is Resource.Loading -> state.copy(isLoading = true)
                    is Resource.Success -> state.copy(trailers = resource.data)
                }
            }
        }
    }
}
