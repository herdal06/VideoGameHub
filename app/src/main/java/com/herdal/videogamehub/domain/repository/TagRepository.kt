package com.herdal.videogamehub.domain.repository

import androidx.paging.PagingData
import com.herdal.videogamehub.domain.ui_model.TagUiModel
import kotlinx.coroutines.flow.Flow

interface TagRepository {
    fun getTags(): Flow<PagingData<TagUiModel>>
}