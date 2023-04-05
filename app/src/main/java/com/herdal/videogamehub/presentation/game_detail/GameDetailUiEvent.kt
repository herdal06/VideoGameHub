package com.herdal.videogamehub.presentation.game_detail

sealed class GameDetailUiEvent {
    data class GetGameById(val id: Int) : GameDetailUiEvent()
    data class GetScreenshots(val gameId: Int) : GameDetailUiEvent()
    data class GetTrailers(val gameId: Int) : GameDetailUiEvent()
}