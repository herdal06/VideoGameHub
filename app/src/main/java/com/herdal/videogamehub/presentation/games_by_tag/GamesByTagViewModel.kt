package com.herdal.videogamehub.presentation.games_by_tag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.herdal.videogamehub.common.Resource
import com.herdal.videogamehub.domain.ui_model.GameUiModel
import com.herdal.videogamehub.domain.use_case.game.AddOrRemoveGameFromFavoriteUseCase
import com.herdal.videogamehub.domain.use_case.game.GetGamesByTagUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamesByTagViewModel @Inject constructor(
    private val getGamesByTagUseCase: GetGamesByTagUseCase,
    private val addOrRemoveGameFromFavoriteUseCase: AddOrRemoveGameFromFavoriteUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(GamesByTagUiState())
    val uiState = _uiState.asStateFlow()

    fun handleEvent(uiEvent: GamesByTagUiEvent) {
        when (uiEvent) {
            is GamesByTagUiEvent.GetGamesByTag -> {
                getGamesByTag(uiEvent.tagId)
            }
            is GamesByTagUiEvent.FavoriteGameIconClicked -> {
                favoriteGameIconClicked(uiEvent.game)
            }
        }
    }

    private fun getGamesByTag(tagId: Int) = viewModelScope.launch {
        getGamesByTagUseCase.invoke(tagId).collect { resource ->
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

    fun favoriteGameIconClicked(game: GameUiModel) {
        viewModelScope.launch {
            addOrRemoveGameFromFavoriteUseCase.invoke(game)
        }
    }
}