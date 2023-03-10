package com.herdal.videogamehub.presentation.favorite_games.adapter

import com.herdal.videogamehub.domain.ui_model.GameUiModel

interface OnFavoriteGameClickHandler {
    fun addGameToFavorite(game: GameUiModel)
}