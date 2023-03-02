package com.herdal.videogamehub.domain.repository

import androidx.paging.PagingData
import com.herdal.videogamehub.domain.ui_model.GameUiModel
import kotlinx.coroutines.flow.Flow

interface GameRepository {
    fun getGames(): Flow<PagingData<GameUiModel>>
}