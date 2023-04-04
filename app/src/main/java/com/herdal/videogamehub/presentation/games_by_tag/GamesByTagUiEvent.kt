package com.herdal.videogamehub.presentation.games_by_tag

import com.herdal.videogamehub.domain.ui_model.GameUiModel

sealed class GamesByTagUiEvent {
    data class GetGamesByTag(val tagId: Int) : GamesByTagUiEvent()
    data class FavoriteGameIconClicked(val game: GameUiModel) : GamesByTagUiEvent()
}