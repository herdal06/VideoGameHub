package com.herdal.videogamehub.presentation.home

import com.herdal.videogamehub.domain.ui_model.GameUiModel

interface OnFavoriteGameClickHandler {
    fun addGameToFavorite(game: GameUiModel)
}