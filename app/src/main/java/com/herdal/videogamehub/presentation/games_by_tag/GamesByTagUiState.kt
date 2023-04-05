package com.herdal.videogamehub.presentation.games_by_tag

import androidx.paging.PagingData
import com.herdal.videogamehub.domain.ui_model.GameUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class GamesByTagUiState(
    val error: String? = null,
    val isLoading: Boolean = false,
    val games: Flow<PagingData<GameUiModel>>? = emptyFlow()
)
