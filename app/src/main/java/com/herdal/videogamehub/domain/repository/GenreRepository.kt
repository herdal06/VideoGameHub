package com.herdal.videogamehub.domain.repository

import com.herdal.videogamehub.data.local.entity.GenreEntity
import com.herdal.videogamehub.data.remote.dto.genre.genre_detail.GenreDetailDto
import com.herdal.videogamehub.utils.Resource
import kotlinx.coroutines.flow.Flow

interface GenreRepository {
    fun getGenres(): Flow<Resource<List<GenreEntity>>>
    suspend fun getGenreDetails(id: Int): GenreDetailDto
}