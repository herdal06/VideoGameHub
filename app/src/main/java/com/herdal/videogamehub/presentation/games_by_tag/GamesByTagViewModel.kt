package com.herdal.videogamehub.presentation.games_by_tag

import androidx.lifecycle.viewModelScope
import com.herdal.videogamehub.common.Resource
import com.herdal.videogamehub.common.base.BaseViewModel
import com.herdal.videogamehub.domain.ui_model.GameUiModel
import com.herdal.videogamehub.domain.use_case.game.AddOrRemoveGameFromFavoriteUseCase
import com.herdal.videogamehub.domain.use_case.game.GetGamesByTagUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamesByTagViewModel @Inject constructor(
    private val getGamesByTagUseCase: GetGamesByTagUseCase,
    private val addOrRemoveGameFromFavoriteUseCase: AddOrRemoveGameFromFavoriteUseCase,
) : BaseViewModel<GamesByTagUiState>() {

    override fun getInitialUiState(): GamesByTagUiState = GamesByTagUiState()

    fun handleEvent(uiEvent: GamesByTagUiEvent) {
        when (uiEvent) {
            is GamesByTagUiEvent.GetGamesByTag -> getGamesByTag(uiEvent.tagId)
            is GamesByTagUiEvent.FavoriteGameIconClicked -> favoriteGameIconClicked(uiEvent.game)
        }
    }

    private fun getGamesByTag(tagId: Int) = viewModelScope.launch {
        getGamesByTagUseCase.invoke(tagId).collect { resource ->
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