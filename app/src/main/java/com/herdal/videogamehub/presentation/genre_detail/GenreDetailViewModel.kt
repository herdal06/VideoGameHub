package com.herdal.videogamehub.presentation.genre_detail

import androidx.lifecycle.viewModelScope
import com.herdal.videogamehub.common.Resource
import com.herdal.videogamehub.common.base.BaseViewModel
import com.herdal.videogamehub.domain.ui_model.GameUiModel
import com.herdal.videogamehub.domain.use_case.game.AddOrRemoveGameFromFavoriteUseCase
import com.herdal.videogamehub.domain.use_case.game.GetGamesByGenreUseCase
import com.herdal.videogamehub.domain.use_case.genre.GetGenreDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenreDetailViewModel @Inject constructor(
    private val getGenreDetailsUseCase: GetGenreDetailsUseCase,
    private val getGamesByGenreUseCase: GetGamesByGenreUseCase,
    private val addOrRemoveGameFromFavoriteUseCase: AddOrRemoveGameFromFavoriteUseCase
) : BaseViewModel<GenreDetailUiState>() {

    override fun getInitialUiState(): GenreDetailUiState = GenreDetailUiState()

    fun handleEvent(event: GenreDetailUiEvent) {
        when (event) {
            is GenreDetailUiEvent.FavoriteGameIconClicked -> favoriteGameIconClicked(event.game)
            is GenreDetailUiEvent.GetGamesByGenre -> getGamesByGenre(event.genreId)
            is GenreDetailUiEvent.GetGenreDetails -> getGenreDetails(event.genreId)
        }
    }

    fun getGenreDetails(genreId: Int) = viewModelScope.launch {
        getGenreDetailsUseCase.invoke(genreId = genreId).collect { resource ->
            updateUiState { state ->
                when (resource) {
                    is Resource.Error -> state.copy(error = resource.message)
                    is Resource.Loading -> state.copy(isLoading = true)
                    is Resource.Success -> state.copy(genre = resource.data)
                }
            }
        }
    }

    fun getGamesByGenre(genreId: Int) = viewModelScope.launch {
        getGamesByGenreUseCase.invoke(genreId = genreId).collect { resource ->
            updateUiState { state ->
                when (resource) {
                    is Resource.Error -> state.copy(error = resource.message)
                    is Resource.Loading -> state.copy(isLoading = true)
                    is Resource.Success -> state.copy(games = resource.data)
                }
            }
        }
    }

    fun favoriteGameIconClicked(game: GameUiModel) = viewModelScope.launch {
        addOrRemoveGameFromFavoriteUseCase.invoke(game)
    }
}