package com.herdal.videogamehub.presentation.game_detail

import com.herdal.videogamehub.domain.ui_model.GameUiModel
import com.herdal.videogamehub.domain.ui_model.ScreenshotUiModel
import com.herdal.videogamehub.domain.ui_model.TrailerUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class GameDetailUiState(
    val error: String? = null,
    val isLoading: Boolean = false,
    val gameDetail: GameUiModel? = null,
    val screenshots: Flow<List<ScreenshotUiModel>?>? = emptyFlow(),
    val trailers: List<TrailerUiModel>? = emptyList()
)