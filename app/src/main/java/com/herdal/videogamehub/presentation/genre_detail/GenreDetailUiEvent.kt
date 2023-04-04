package com.herdal.videogamehub.presentation.genre_detail

import com.herdal.videogamehub.domain.ui_model.GameUiModel

sealed class GenreDetailUiEvent {
    data class GetGenreDetails(val genreId: Int) : GenreDetailUiEvent()
    data class GetGamesByGenre(val genreId: Int) : GenreDetailUiEvent()
    data class FavoriteGameIconClicked(val game: GameUiModel) : GenreDetailUiEvent()
}