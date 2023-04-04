package com.herdal.videogamehub.presentation.genre_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.herdal.videogamehub.common.Resource
import com.herdal.videogamehub.domain.ui_model.GameUiModel
import com.herdal.videogamehub.domain.use_case.game.AddOrRemoveGameFromFavoriteUseCase
import com.herdal.videogamehub.domain.use_case.game.GetGamesByGenreUseCase
import com.herdal.videogamehub.domain.use_case.genre.GetGenreDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenreDetailViewModel @Inject constructor(
    private val getGenreDetailsUseCase: GetGenreDetailsUseCase,
    private val getGamesByGenreUseCase: GetGamesByGenreUseCase,
    private val addOrRemoveGameFromFavoriteUseCase: AddOrRemoveGameFromFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(GenreDetailUiState())
    val uiState: StateFlow<GenreDetailUiState> = _uiState.asStateFlow()

    fun handleEvent(event: GenreDetailUiEvent) {
        when (event) {
            is GenreDetailUiEvent.FavoriteGameIconClicked -> favoriteGameIconClicked(event.game)
            is GenreDetailUiEvent.GetGamesByGenre -> getGamesByGenre(event.genreId)
            is GenreDetailUiEvent.GetGenreDetails -> getGenreDetails(event.genreId)
        }
    }

    fun getGenreDetails(genreId: Int) {
        getGenreDetailsUseCase.invoke(genreId = genreId)
            .onEach { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _uiState.update { state ->
                            state.copy(isLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        _uiState.update { state ->
                            state.copy(genre = resource.data)
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update { state ->
                            state.copy(error = resource.message)
                        }
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun getGamesByGenre(genreId: Int) {
        getGamesByGenreUseCase.invoke(genreId = genreId)
            .onEach { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _uiState.update { state ->
                            state.copy(isLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        _uiState.update { state ->
                            state.copy(games = resource.data)
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update { state ->
                            state.copy(error = resource.message)
                        }
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun favoriteGameIconClicked(game: GameUiModel) {
        viewModelScope.launch {
            addOrRemoveGameFromFavoriteUseCase.invoke(game)
        }
    }
}