package com.herdal.videogamehub.presentation.home

import androidx.paging.PagingData
import com.herdal.videogamehub.domain.ui_model.GameUiModel
import com.herdal.videogamehub.domain.ui_model.GenreUiModel
import com.herdal.videogamehub.domain.ui_model.StoreUiModel
import com.herdal.videogamehub.domain.ui_model.TagUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class HomeUiState(
    val games: Flow<PagingData<GameUiModel>>? = emptyFlow(),
    val genres: Flow<List<GenreUiModel>?>? = emptyFlow(),
    val stores: Flow<List<StoreUiModel>?>? = emptyFlow(),
    val tags: Flow<PagingData<TagUiModel>>? = emptyFlow(),
    val error: String? = null,
    val isLoading: Boolean = false
)