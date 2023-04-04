package com.herdal.videogamehub.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.herdal.videogamehub.common.Resource
import com.herdal.videogamehub.domain.ui_model.GameUiModel
import com.herdal.videogamehub.domain.use_case.game.AddOrRemoveGameFromFavoriteUseCase
import com.herdal.videogamehub.domain.use_case.game.GetGamesByGenreUseCase
import com.herdal.videogamehub.domain.use_case.game.SearchGamesUseCase
import com.herdal.videogamehub.domain.use_case.genre.GetGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchGamesUseCase: SearchGamesUseCase,
    private val addOrRemoveGameFromFavoriteUseCase: AddOrRemoveGameFromFavoriteUseCase,
    private val getGenresUseCase: GetGenresUseCase,
    private val getGamesByGenreUseCase: GetGamesByGenreUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    fun handleEvent(uiEvent: SearchUiEvent) {
        when (uiEvent) {
            is SearchUiEvent.SearchGames -> {
                searchGames(uiEvent.searchQuery)
            }
            is SearchUiEvent.FavoriteGameIconClicked -> {
                favoriteGameIconClicked(uiEvent.game)
            }
            is SearchUiEvent.GetGamesByGenre -> {
                getGamesByGenre(uiEvent.genreId)
            }
            SearchUiEvent.GetGenres -> {
                getGenres()
            }
        }
    }

    fun favoriteGameIconClicked(game: GameUiModel) {
        viewModelScope.launch {
            addOrRemoveGameFromFavoriteUseCase.invoke(game)
        }
    }

    private fun getGamesByGenre(genreId: Int) = viewModelScope.launch {
        getGamesByGenreUseCase.invoke(genreId = genreId).collect { resource ->
            when (resource) {
                is Resource.Success -> {
                    _uiState.update { state ->
                        state.copy(gamesByGenre = resource.data)
                    }
                }
                is Resource.Error -> {
                    _uiState.update { state ->
                        state.copy(error = resource.message)
                    }
                }
                is Resource.Loading -> {
                    _uiState.update { state ->
                        state.copy(isLoading = true)
                    }
                }
            }
        }
    }

    private fun getGenres() = viewModelScope.launch {
        getGenresUseCase().collect { resource ->
            when (resource) {
                is Resource.Success -> {
                    _uiState.update { state ->
                        state.copy(genres = resource.data)
                    }
                }
                is Resource.Error -> {
                    _uiState.update { state ->
                        state.copy(error = resource.message)
                    }
                }
                is Resource.Loading -> {
                    _uiState.update { state ->
                        state.copy(isLoading = true)
                    }
                }
            }
        }
    }

    private fun searchGames(query: String) = viewModelScope.launch {
        if (query.isNotEmpty()) {
            searchGamesUseCase.invoke(searchQuery = query).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _uiState.update { state ->
                            state.copy(gamesByGenre = resource.data)
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update { state ->
                            state.copy(error = resource.message)
                        }
                    }
                    is Resource.Loading -> {
                        _uiState.update { state ->
                            state.copy(isLoading = true)
                        }
                    }
                }
            }
        }
    }
}