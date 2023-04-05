package com.herdal.videogamehub.presentation.home

import androidx.lifecycle.viewModelScope
import com.herdal.videogamehub.common.Resource
import com.herdal.videogamehub.common.base.BaseViewModel
import com.herdal.videogamehub.domain.ui_model.GameUiModel
import com.herdal.videogamehub.domain.use_case.game.AddOrRemoveGameFromFavoriteUseCase
import com.herdal.videogamehub.domain.use_case.game.GetGamesUseCase
import com.herdal.videogamehub.domain.use_case.genre.GetGenresUseCase
import com.herdal.videogamehub.domain.use_case.store.GetStoresUseCase
import com.herdal.videogamehub.domain.use_case.tag.GetTagsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getGamesUseCase: GetGamesUseCase,
    private val getGenresUseCase: GetGenresUseCase,
    private val getStoresUseCase: GetStoresUseCase,
    private val addOrRemoveGameFromFavoriteUseCase: AddOrRemoveGameFromFavoriteUseCase,
    private val getTagsUseCase: GetTagsUseCase
) : BaseViewModel<HomeUiState>() {

    override fun getInitialUiState(): HomeUiState = HomeUiState()

    fun handleEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.FavoriteGameIconClicked -> favoriteGameIconClicked(event.game)
            is HomeUiEvent.GetGames -> getGames()
            is HomeUiEvent.GetTags -> getTags()
            is HomeUiEvent.GetGenres -> getGenres()
            is HomeUiEvent.GetStores -> getStores()
        }
    }

    private fun getStores() = viewModelScope.launch {
        getStoresUseCase.invoke().collect { resource ->
            updateUiState { state ->
                when (resource) {
                    is Resource.Error -> state.copy(error = resource.message)
                    is Resource.Loading -> state.copy(isLoading = true)
                    is Resource.Success -> state.copy(stores = resource.data)
                }
            }
        }
    }

    private fun getGenres() = viewModelScope.launch {
        getGenresUseCase.invoke().collect { resource ->
            updateUiState { state ->
                when (resource) {
                    is Resource.Error -> state.copy(error = resource.message)
                    is Resource.Loading -> state.copy(isLoading = true)
                    is Resource.Success -> state.copy(genres = resource.data)
                }
            }
        }
    }

    private fun getGames() = viewModelScope.launch {
        getGamesUseCase.invoke().collect { resource ->
            updateUiState { state ->
                when (resource) {
                    is Resource.Error -> state.copy(error = resource.message)
                    is Resource.Loading -> state.copy(isLoading = true)
                    is Resource.Success -> state.copy(games = resource.data)
                }
            }
        }
    }

    private fun getTags() = viewModelScope.launch {
        getTagsUseCase.invoke().collect { resource ->
            updateUiState { state ->
                when (resource) {
                    is Resource.Error -> state.copy(error = resource.message)
                    is Resource.Loading -> state.copy(isLoading = true)
                    is Resource.Success -> state.copy(tags = resource.data)
                }
            }
        }
    }

    private fun favoriteGameIconClicked(game: GameUiModel) = viewModelScope.launch {
        addOrRemoveGameFromFavoriteUseCase.invoke(game)
    }
}