package com.herdal.videogamehub.presentation.search

import androidx.lifecycle.viewModelScope
import com.herdal.videogamehub.common.Resource
import com.herdal.videogamehub.common.base.BaseViewModel
import com.herdal.videogamehub.domain.ui_model.GameUiModel
import com.herdal.videogamehub.domain.use_case.game.AddOrRemoveGameFromFavoriteUseCase
import com.herdal.videogamehub.domain.use_case.game.GetGamesByGenreUseCase
import com.herdal.videogamehub.domain.use_case.game.SearchGamesUseCase
import com.herdal.videogamehub.domain.use_case.genre.GetGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchGamesUseCase: SearchGamesUseCase,
    private val addOrRemoveGameFromFavoriteUseCase: AddOrRemoveGameFromFavoriteUseCase,
    private val getGenresUseCase: GetGenresUseCase,
    private val getGamesByGenreUseCase: GetGamesByGenreUseCase,
) : BaseViewModel<SearchUiState>() {

    override fun getInitialUiState(): SearchUiState = SearchUiState()

    fun handleEvent(uiEvent: SearchUiEvent) {
        when (uiEvent) {
            is SearchUiEvent.SearchGames -> searchGames(uiEvent.searchQuery)
            is SearchUiEvent.FavoriteGameIconClicked -> favoriteGameIconClicked(uiEvent.game)
            is SearchUiEvent.GetGamesByGenre -> getGamesByGenre(uiEvent.genreId)
            is SearchUiEvent.GetGenres -> getGenres()

        }
    }

    private fun getGamesByGenre(genreId: Int) = viewModelScope.launch {
        getGamesByGenreUseCase.invoke(genreId = genreId).collect { resource ->
            updateUiState { state ->
                when (resource) {
                    is Resource.Error -> state.copy(error = resource.message)
                    is Resource.Loading -> state.copy(isLoading = true)
                    is Resource.Success -> state.copy(gamesByGenre = resource.data)
                }
            }
        }
    }

    private fun getGenres() = viewModelScope.launch {
        getGenresUseCase().collect { resource ->
            updateUiState { state ->
                when (resource) {
                    is Resource.Error -> state.copy(error = resource.message)
                    is Resource.Loading -> state.copy(isLoading = true)
                    is Resource.Success -> state.copy(genres = resource.data)
                }
            }
        }
    }

    private fun searchGames(query: String) = viewModelScope.launch {
        if (query.isNotEmpty()) {
            searchGamesUseCase.invoke(searchQuery = query).collect { resource ->
                updateUiState { state ->
                    when (resource) {
                        is Resource.Error -> state.copy(error = resource.message)
                        is Resource.Loading -> state.copy(isLoading = true)
                        is Resource.Success -> state.copy(searchedGames = resource.data)
                    }
                }
            }
        }
    }

    fun favoriteGameIconClicked(game: GameUiModel) = viewModelScope.launch {
        addOrRemoveGameFromFavoriteUseCase.invoke(game)
    }
}