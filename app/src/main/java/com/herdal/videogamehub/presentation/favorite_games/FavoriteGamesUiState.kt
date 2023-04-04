package com.herdal.videogamehub.presentation.favorite_games

import com.herdal.videogamehub.domain.ui_model.GameUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class FavoriteGamesUiState(
    val error: String? = null,
    val isLoading: Boolean = false,
    val favoriteGames: Flow<List<GameUiModel>>? = emptyFlow(),
    val searchedGames: List<GameUiModel>? = null
)