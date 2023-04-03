package com.herdal.videogamehub.presentation.home.adapter.game

import com.herdal.videogamehub.domain.ui_model.GameUiModel

interface OnGameClickListener {
    fun onGameClick(gameId: Int)
    fun onFavoriteGameClick(game: GameUiModel)
}