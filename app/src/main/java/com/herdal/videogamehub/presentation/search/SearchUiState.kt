package com.herdal.videogamehub.presentation.search

import androidx.paging.PagingData
import com.herdal.videogamehub.domain.ui_model.GameUiModel
import com.herdal.videogamehub.domain.ui_model.GenreUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class SearchUiState(
    val error: String? = null,
    val isLoading: Boolean = false,
    val searchedGames: Flow<PagingData<GameUiModel>>? = emptyFlow(),
    val genres: Flow<List<GenreUiModel>?>? = emptyFlow(),
    val gamesByGenre: Flow<PagingData<GameUiModel>>? = emptyFlow()
)