package com.herdal.videogamehub.presentation.game_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.herdal.videogamehub.common.Resource
import com.herdal.videogamehub.domain.ui_model.GameUiModel
import com.herdal.videogamehub.domain.ui_model.ScreenshotUiModel
import com.herdal.videogamehub.domain.ui_model.TrailerUiModel
import com.herdal.videogamehub.domain.use_case.GetGameDetailsUseCase
import com.herdal.videogamehub.domain.use_case.GetGameTrailersUseCase
import com.herdal.videogamehub.domain.use_case.GetScreenshotsUseCase
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
    private val _gameDetail =
        MutableStateFlow<Resource<GameUiModel>>(Resource.Loading())
    val gameDetail = _gameDetail.asStateFlow()

    private val _screenshots =
        MutableStateFlow<List<ScreenshotUiModel>>(emptyList())
    val screenshots = _screenshots.asStateFlow()

    private val _trailers =
        MutableStateFlow<Resource<List<TrailerUiModel>?>>(Resource.Loading())
    val trailers = _trailers.asStateFlow()

    fun getGameById(id: Int) {
        getGameDetailsUseCase.invoke(id)
            .onEach { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _gameDetail.value = Resource.Loading()
                    }
                    is Resource.Success -> {
                        if (resource.data != null) {
                            _gameDetail.value = Resource.Success(resource.data)
                        }
                    }
                    is Resource.Error -> {
                        _gameDetail.value = Resource.Error(resource.message)
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun getScreenshots(gameId: Int) {
        getScreenshotsUseCase(gameId).onEach {
            it?.let { _screenshots.emit(it) }
        }.launchIn(viewModelScope)
    }

    fun getTrailers(gameId: Int) = viewModelScope.launch {
        getGameTrailersUseCase(gameId).onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _trailers.value = Resource.Loading()
                }
                is Resource.Success -> {
                    if (resource.data != null) {
                        _trailers.value = Resource.Success(resource.data)
                    }
                }
                is Resource.Error -> {
                    _trailers.value = Resource.Error(resource.message)
                }
            }
        }.launchIn(viewModelScope)
    }
}
