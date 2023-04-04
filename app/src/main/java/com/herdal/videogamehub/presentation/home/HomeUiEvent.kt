package com.herdal.videogamehub.presentation.home

import com.herdal.videogamehub.domain.ui_model.GameUiModel

sealed class HomeUiEvent {
    object GetGames : HomeUiEvent()
    object GetTags : HomeUiEvent()
    data class FavoriteGameIconClicked(val game: GameUiModel) : HomeUiEvent()
    object GetStores : HomeUiEvent()
    object GetGenres : HomeUiEvent()
}