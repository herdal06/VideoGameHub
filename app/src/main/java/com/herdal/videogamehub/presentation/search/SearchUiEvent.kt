package com.herdal.videogamehub.presentation.search

import com.herdal.videogamehub.domain.ui_model.GameUiModel

sealed class SearchUiEvent {
    data class SearchGames(val searchQuery: String) : SearchUiEvent()
    data class FavoriteGameIconClicked(val game: GameUiModel) : SearchUiEvent()
    data class GetGamesByGenre(val genreId: Int) : SearchUiEvent()
    object GetGenres : SearchUiEvent()
}