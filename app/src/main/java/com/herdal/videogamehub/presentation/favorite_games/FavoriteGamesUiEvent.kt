package com.herdal.videogamehub.presentation.favorite_games

import com.herdal.videogamehub.domain.ui_model.GameUiModel

sealed class FavoriteGamesUiEvent {
    data class FavoriteIconClicked(val game: GameUiModel) : FavoriteGamesUiEvent()
    data class SearchQueryChanged(val searchQuery: String) : FavoriteGamesUiEvent()
    object GetFavoriteGames : FavoriteGamesUiEvent()
}