package com.herdal.videogamehub.domain.repository

import com.herdal.videogamehub.data.local.entity.GenreEntity
import com.herdal.videogamehub.utils.Resource
import kotlinx.coroutines.flow.Flow

interface GenreRepository {
    fun getGenres(): Flow<Resource<List<GenreEntity>>>
}