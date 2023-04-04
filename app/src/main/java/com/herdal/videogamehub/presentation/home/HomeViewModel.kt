package com.herdal.videogamehub.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.herdal.videogamehub.common.Resource
import com.herdal.videogamehub.domain.ui_model.GameUiModel
import com.herdal.videogamehub.domain.use_case.game.AddOrRemoveGameFromFavoriteUseCase
import com.herdal.videogamehub.domain.use_case.game.GetGamesUseCase
import com.herdal.videogamehub.domain.use_case.genre.GetGenresUseCase
import com.herdal.videogamehub.domain.use_case.store.GetStoresUseCase
import com.herdal.videogamehub.domain.use_case.tag.GetTagsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getGamesUseCase: GetGamesUseCase,
    private val getGenresUseCase: GetGenresUseCase,
    private val getStoresUseCase: GetStoresUseCase,
    private val addOrRemoveGameFromFavoriteUseCase: AddOrRemoveGameFromFavoriteUseCase,
    private val getTagsUseCase: GetTagsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

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
            when (resource) {
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
                is Resource.Success -> {
                    _uiState.update { state ->
                        state.copy(stores = resource.data)
                    }
                }
            }
        }
    }

    private fun getGenres() = viewModelScope.launch {
        getGenresUseCase.invoke().collect { resource ->
            when (resource) {
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
                is Resource.Success -> {
                    _uiState.update { state ->
                        state.copy(genres = resource.data)
                    }
                }
            }
        }
    }

    private fun getGames() = viewModelScope.launch {
        getGamesUseCase.invoke().collect { resource ->
            when (resource) {
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
                is Resource.Loading -> {
                    _uiState.update { state ->
                        state.copy(isLoading = true)
                    }
                }
            }
        }
    }

    private fun favoriteGameIconClicked(game: GameUiModel) {
        viewModelScope.launch {
            addOrRemoveGameFromFavoriteUseCase.invoke(game)
        }
    }

    private fun getTags() = viewModelScope.launch {
        getTagsUseCase.invoke().collect { resource ->
            when (resource) {
                is Resource.Success -> {
                    _uiState.update { state ->
                        state.copy(tags = resource.data, isLoading = false)
                    }
                }
                is Resource.Error -> {
                    _uiState.update { state ->
                        state.copy(error = resource.message, isLoading = false)
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

/*private fun <T : Any> getItems(
    useCase: Flow<Resource<PagingData<T>>>,
    flow: MutableStateFlow<PagingData<T>?>
) = viewModelScope.launch {
    useCase.collect { resource ->
        when (resource) {
            is Resource.Success -> {
                flow.value = resource.data
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
}*/
