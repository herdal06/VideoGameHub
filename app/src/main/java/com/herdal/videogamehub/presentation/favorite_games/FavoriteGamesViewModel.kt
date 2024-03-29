package com.herdal.videogamehub.presentation.favorite_games

import androidx.lifecycle.viewModelScope
import com.herdal.videogamehub.common.Resource
import com.herdal.videogamehub.common.base.BaseViewModel
import com.herdal.videogamehub.domain.ui_model.GameUiModel
import com.herdal.videogamehub.domain.use_case.game.AddOrRemoveGameFromFavoriteUseCase
import com.herdal.videogamehub.domain.use_case.game.GetFavoriteGamesUseCase
import com.herdal.videogamehub.domain.use_case.game.SearchFavoriteGamesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteGamesViewModel @Inject constructor(
    private val getFavoriteGamesUseCase: GetFavoriteGamesUseCase,
    private val addOrRemoveGameFromFavoriteUseCase: AddOrRemoveGameFromFavoriteUseCase,
    private val searchFavoriteGamesUseCase: SearchFavoriteGamesUseCase
) : BaseViewModel<FavoriteGamesUiState>() {

    override fun getInitialUiState(): FavoriteGamesUiState = FavoriteGamesUiState()

    fun handleEvent(event: FavoriteGamesUiEvent) {
        when (event) {
            is FavoriteGamesUiEvent.FavoriteIconClicked -> favoriteIconClicked(event.game)
            is FavoriteGamesUiEvent.SearchQueryChanged -> searchFavGames(event.searchQuery)
            is FavoriteGamesUiEvent.GetFavoriteGames -> getFavoriteGames()
        }
    }

    private fun getFavoriteGames() = viewModelScope.launch {
        getFavoriteGamesUseCase.invoke().collect { resource ->
            updateUiState { state ->
                when (resource) {
                    is Resource.Error -> state.copy(error = resource.message)
                    is Resource.Loading -> state.copy(isLoading = true)
                    is Resource.Success -> state.copy(favoriteGames = resource.data)
                }
            }
        }
    }

    private fun searchFavGames(searchQuery: String) = viewModelScope.launch {
        searchFavoriteGamesUseCase(searchQuery = searchQuery).collect { resource ->
            updateUiState { state ->
                when (resource) {
                    is Resource.Error -> state.copy(error = resource.message)
                    is Resource.Loading -> state.copy(isLoading = true)
                    is Resource.Success -> state.copy(searchedGames = resource.data)
                }
            }
        }
    }

    private fun favoriteIconClicked(game: GameUiModel) = viewModelScope.launch {
        addOrRemoveGameFromFavoriteUseCase.invoke(game)
    }
}