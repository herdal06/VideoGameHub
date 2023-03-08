package com.herdal.videogamehub.domain.repository

import com.herdal.videogamehub.data.local.entity.ScreenshotEntity
import com.herdal.videogamehub.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ScreenshotRepository {
    fun getGameScreenshots(gameId: Int): Flow<Resource<List<ScreenshotEntity>>>
}