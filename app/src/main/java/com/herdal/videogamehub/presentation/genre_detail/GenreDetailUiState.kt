package com.herdal.videogamehub.presentation.genre_detail

import androidx.paging.PagingData
import com.herdal.videogamehub.domain.ui_model.GameUiModel
import com.herdal.videogamehub.domain.ui_model.GenreUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class GenreDetailUiState(
    val error: String? = null,
    val isLoading: Boolean = false,
    val genre: GenreUiModel? = null,
    val games: Flow<PagingData<GameUiModel>>? = emptyFlow()
)